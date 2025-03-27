package com.example.POS_MJ_BACK.services;

import com.example.POS_MJ_BACK.models.Producto;
import com.example.POS_MJ_BACK.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Transactional
    public Producto crearProducto(Producto producto) {
        // Validar que el precio sea positivo
        if (producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        return productoRepository.save(producto);
    }

    public Page<Producto> obtenerTodosProductos(int page, int limit) {
        return productoRepository.findAll(PageRequest.of(page, limit));
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setPrecio(productoActualizado.getPrecio());
                    producto.setStock(productoActualizado.getStock());
                    producto.setActivo(productoActualizado.getActivo());
                    return productoRepository.save(producto);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Transactional
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Transactional
    public void desactivarProducto(Long id) {
        productoRepository.findById(id)
                .ifPresent(producto -> {
                    producto.setActivo(false);
                    productoRepository.save(producto);
                });
    }

    @Transactional
    public void activarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(true);
        productoRepository.save(producto);
    }
}