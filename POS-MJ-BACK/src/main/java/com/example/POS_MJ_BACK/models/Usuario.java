package com.example.POS_MJ_BACK.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100, unique = true)
    private String usuario;

    @Column(nullable = false, length = 100, unique = true)
    @Email(message = "Debe ser un correo electronico valido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @Column(nullable = false, length = 255)
    private String contrasenha;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;
}
