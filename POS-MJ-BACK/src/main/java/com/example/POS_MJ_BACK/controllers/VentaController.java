package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.models.Venta;
import com.example.POS_MJ_BACK.services.VentaService;
import com.example.POS_MJ_BACK.dto.VentaConDetallesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<Page<Venta>> obtenerTodasVentas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ventaService.obtenerTodasVentas(page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaConDetalles(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerVentaConDetalles(id));
    }

    @PostMapping
    public ResponseEntity<Venta> crearVenta(
            @RequestBody VentaConDetallesDTO ventaConDetalles) {
        return ResponseEntity.ok(
                ventaService.crearVentaCompleta(
                        ventaConDetalles.getVenta(),
                        ventaConDetalles.getDetalles()
                )
        );
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