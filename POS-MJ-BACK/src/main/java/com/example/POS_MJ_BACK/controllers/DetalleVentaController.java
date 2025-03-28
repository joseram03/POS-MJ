package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.models.DetalleVenta;
import com.example.POS_MJ_BACK.services.DetalleVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalles-venta")
@RequiredArgsConstructor
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<Page<DetalleVenta>> obtenerDetallesPorVenta(
            @PathVariable Long ventaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(detalleVentaService.obtenerDetallesPorVenta(ventaId, page, limit));
    }

    @PostMapping
    public ResponseEntity<DetalleVenta> crearDetalleVenta(@RequestBody DetalleVenta detalleVenta) {
        return ResponseEntity.ok(detalleVentaService.crearDetalleVenta(detalleVenta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleVenta(@PathVariable Long id) {
        detalleVentaService.eliminarDetalleVenta(id);
        return ResponseEntity.noContent().build();
    }
}