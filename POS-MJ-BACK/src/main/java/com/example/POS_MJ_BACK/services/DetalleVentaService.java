package com.example.POS_MJ_BACK.services;

import com.example.POS_MJ_BACK.dto.DetalleVentaDTO;
import com.example.POS_MJ_BACK.dto.VentaDTO;
import com.example.POS_MJ_BACK.exceptions.RecursoNoEncontradoException;
import com.example.POS_MJ_BACK.models.DetalleVenta;
import com.example.POS_MJ_BACK.models.Producto;
import com.example.POS_MJ_BACK.models.Venta;
import com.example.POS_MJ_BACK.repositories.DetalleVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoService productoService;

    public DetalleVenta crearDetalleVenta(DetalleVenta detalleVenta) {
        // Validar stock disponible
        Producto producto = productoService.obtenerProductoPorId(detalleVenta.getProducto().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        if (producto.getStock() < detalleVenta.getCantidad()) {
            throw new RecursoNoEncontradoException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        // Actualizar stock
        producto.setStock(producto.getStock() - detalleVenta.getCantidad());
        productoService.actualizarProducto(producto.getId(), producto);

        return detalleVentaRepository.save(detalleVenta);
    }

    public VentaDTO obtenerDetallesPorVenta(Venta venta, int page, int size) {

        // Obtener detalles paginados
        Pageable pageable = PageRequest.of(page, size);
        Page<DetalleVenta> detallesPaginados = detalleVentaRepository.findByVentaId(venta.getId(), pageable);

        // Convertir DetalleVenta a DetalleVentaDTO
        List<DetalleVentaDTO> detallesDTO = detallesPaginados.getContent().stream()
                .map(detalle -> new DetalleVentaDTO(
                        detalle.getId(),
                        detalle.getProducto().getNombre(), // Obtener el nombre del producto
                        detalle.getCantidad(),
                        detalle.getSubtotal()
                ))
                .collect(Collectors.toList());

        // Convertir lista de DTO a Page<DetalleVentaDTO>
        Page<DetalleVentaDTO> detallesDTOPage = new PageImpl<>(detallesDTO, pageable, detallesPaginados.getTotalElements());

        // Retornar DTO con la venta y los detalles paginados
        return new VentaDTO(
                venta.getId(),
                venta.getFecha(),
                venta.getTotal(),
                venta.getMetodoPago(),
                venta.getUsuario().getNombre(),
                detallesDTOPage
        );
    }

    public void eliminarDetalleVenta(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}