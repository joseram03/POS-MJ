package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.models.Venta;
import com.example.POS_MJ_BACK.services.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<Page<Venta>> obtenerTodasVentas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ventaService.obtenerTodasVentas(page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<Venta> obtenerVentaConDetalles(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerVentaConDetalles(id));
    }

    @PostMapping
    public ResponseEntity<Venta> crearVenta(
            @RequestBody Venta venta,
            @RequestBody List<VentaService.DetalleVentaRequest> detalles) {
        return ResponseEntity.ok(ventaService.crearVentaCompleta(venta, detalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        if (!ventaService.existeVenta(id)) {
            return ResponseEntity.notFound().build();
        }
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}