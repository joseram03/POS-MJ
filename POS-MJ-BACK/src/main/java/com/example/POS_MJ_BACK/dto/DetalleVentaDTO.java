package com.example.POS_MJ_BACK.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DetalleVentaDTO {
    private Long id;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal subtotal;
}
