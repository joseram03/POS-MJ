package com.example.POS_MJ_BACK.dto;

import com.example.POS_MJ_BACK.models.Venta;
import com.example.POS_MJ_BACK.services.VentaService;
import lombok.Data;
import java.util.List;

@Data
public class VentaConDetallesDTO {
    private Venta venta;
    private List<VentaService.DetalleVentaRequest> detalles;
}