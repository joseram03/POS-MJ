package com.example.POS_MJ_BACK.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/*
*   Se utiliza para la api (/api/detalles-venta/venta/1) para que muestre en RespuestaDTO.data
*   Muestra una venta y los detalles venta sin el atributo "venta" nuevamente
*/
@Data
@AllArgsConstructor
public class VentaDTO {
    private Long id;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String metodoPago;
    private String usuario;
    private Page<DetalleVentaDTO> detalles;
}
