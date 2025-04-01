package com.example.POS_MJ_BACK.services;

import com.example.POS_MJ_BACK.models.*;
import com.example.POS_MJ_BACK.repositories.VentaRepository;
import com.example.POS_MJ_BACK.exceptions.RecursoNoEncontradoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaService detalleVentaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Transactional
    public Venta crearVentaCompleta(Venta venta, List<DetalleVentaRequest> detallesRequest) {
        // Validar usuario
        Usuario usuario = usuarioService.obtenerUsuarioPorId(venta.getUsuario().getId());

        // Validar método de pago
        if (!venta.getMetodoPago().matches("(?i)EFECTIVO|TARJETA|TRANSFERENCIA")) {
            throw new IllegalArgumentException("Método de pago no válido");
        }

        // Crear y guardar venta inicial
        venta.setUsuario(usuario);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(BigDecimal.ZERO); // Temporal
        Venta ventaGuardada = ventaRepository.save(venta);

        // Procesar detalles
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVentaRequest detalleReq : detallesRequest) {
            Producto producto = productoService.obtenerProductoPorId(detalleReq.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            int cantidad = detalleReq.getCantidad();

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(ventaGuardada);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setSubtotal(producto.getPrecio().multiply(new BigDecimal(cantidad)));

            detalleVentaService.crearDetalleVenta(detalle);
            total = total.add(detalle.getSubtotal());
        }

        ventaGuardada.setTotal(total);
        return ventaRepository.save(ventaGuardada);
    }

    public Page<Venta> obtenerTodasVentas(int page, int limit) {
        return ventaRepository.findAll(PageRequest.of(page, limit));
    }

    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public Venta obtenerVentaConDetalles(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la venta con ID: " + id));
    }

    @Transactional
    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    public boolean existeVenta(Long id) {
        return ventaRepository.existsById(id);
    }

    // Clase interna para manejar la request de detalles
    public static class DetalleVentaRequest {
        private Long productoId;  // Cambiado de "Producto producto" a "Long productoId"
        private Integer cantidad;

        // Getters y Setters
        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
    }
}