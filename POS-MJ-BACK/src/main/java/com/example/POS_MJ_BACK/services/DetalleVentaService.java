package com.example.POS_MJ_BACK.services;

import com.example.POS_MJ_BACK.models.DetalleVenta;
import com.example.POS_MJ_BACK.models.Producto;
import com.example.POS_MJ_BACK.repositories.DetalleVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoService productoService;

    public DetalleVenta crearDetalleVenta(DetalleVenta detalleVenta) {
        // Validar stock disponible
        Producto producto = productoService.obtenerProductoPorId(detalleVenta.getProducto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (producto.getStock() < detalleVenta.getCantidad()) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        // Actualizar stock
        producto.setStock(producto.getStock() - detalleVenta.getCantidad());
        productoService.actualizarProducto(producto.getId(), producto);

        return detalleVentaRepository.save(detalleVenta);
    }

    public Page<DetalleVenta> obtenerDetallesPorVenta(Long ventaId, int page, int limit) {
        return detalleVentaRepository.findByVentaId(ventaId, PageRequest.of(page, limit));
    }

    public void eliminarDetalleVenta(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}