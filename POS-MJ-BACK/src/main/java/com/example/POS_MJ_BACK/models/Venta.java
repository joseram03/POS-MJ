package com.example.POS_MJ_BACK.models;

import com.example.POS_MJ_BACK.models.DetalleVenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El total es obligatorio")
    //@DecimalMin(value = "0.01", message = "El total debe ser mayor que 0")
    @Digits(integer = 8, fraction = 2, message = "Formato de total inválido (máximo 8 enteros y 2 decimales)")
    private BigDecimal total;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "El método de pago no puede estar vacío")
    @Size(max = 20, message = "El método de pago no puede exceder los 20 caracteres")
    @Pattern(regexp = "^(EFECTIVO|TARJETA|TRANSFERENCIA)$",
            message = "Método de pago no válido. Debe ser: EFECTIVO, TARJETA o TRANSFERENCIA")
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;


    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<DetalleVenta>();
}