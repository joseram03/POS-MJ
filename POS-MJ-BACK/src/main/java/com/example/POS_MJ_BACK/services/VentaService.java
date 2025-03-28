package com.example.POS_MJ_BACK.services;

import com.example.POS_MJ_BACK.models.*;
import com.example.POS_MJ_BACK.repositories.VentaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaService detalleVentaService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

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
            Producto producto = productoService.obtenerProductoPorId(detalleReq.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            // Validar stock
            if (producto.getStock() < detalleReq.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + producto.getNombre());
            }

            // Calcular subtotal
            BigDecimal subtotal = producto.getPrecio().multiply(new BigDecimal(detalleReq.getCantidad()));

            // Crear y guardar detalle
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(ventaGuardada);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleReq.getCantidad());
            detalle.setSubtotal(subtotal);
            detalleVentaService.crearDetalleVenta(detalle);

            total = total.add(subtotal);

            // Actualizar stock
            producto.setStock(producto.getStock() - detalleReq.getCantidad());
            productoService.actualizarProducto(producto.getId(), producto);
        }

        // Actualizar total de la venta
        ventaGuardada.setTotal(total);
        return ventaRepository.save(ventaGuardada);
    }

    public Page<Venta> obtenerTodasVentas(int page, int limit) {
        return ventaRepository.findAll(PageRequest.of(page, limit));
    }

    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta obtenerVentaConDetalles(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));
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
        private Producto producto;
        private Integer cantidad;

        // Getters y Setters
        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
    }
}