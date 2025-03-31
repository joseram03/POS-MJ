package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.dto.DetalleVentaDTO;
import com.example.POS_MJ_BACK.dto.RespuestaDTO;
import com.example.POS_MJ_BACK.dto.VentaDTO;
import com.example.POS_MJ_BACK.models.DetalleVenta;
import com.example.POS_MJ_BACK.models.Venta;
import com.example.POS_MJ_BACK.services.DetalleVentaService;
import com.example.POS_MJ_BACK.services.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalles-venta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @Autowired
    private VentaService ventaService;

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<RespuestaDTO<VentaDTO>> obtenerDetallesPorVenta(
            @PathVariable Long ventaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            //Recuperamos el objeto Venta
            Venta venta = ventaService.obtenerVentaPorId(ventaId);
            VentaDTO ventaDTO = detalleVentaService.obtenerDetallesPorVenta(venta, page, limit);

            return ResponseEntity.ok(new RespuestaDTO<VentaDTO>(
                    HttpStatus.OK.value(),
                    "Respuesta Optenida exitosamente",
                    ventaDTO
            ));
        }catch (Exception e){
            return ResponseEntity.ok(new RespuestaDTO<VentaDTO>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    null
            ));
        }
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