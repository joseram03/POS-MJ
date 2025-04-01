package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.dto.LoginRequest;
import com.example.POS_MJ_BACK.dto.RespuestaDTO;
import com.example.POS_MJ_BACK.models.Usuario;
import com.example.POS_MJ_BACK.services.UsuarioService;
import com.example.POS_MJ_BACK.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @PostMapping("/registro")
    public ResponseEntity<RespuestaDTO<Void>> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            // Recupera el rol como un objeto completo ( se puede pasar el rol parcialmente)
            usuario.setRol(rolService.obtenerRol(usuario.getRol()));

            // Guardar el usuario
            Usuario usuarioRegistrado = usuarioService.crearUsuario(usuario);

            return ResponseEntity.ok(new RespuestaDTO(
                    HttpStatus.OK.value(),
                    "Usuario registrado exitosamente",
                    usuarioRegistrado
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RespuestaDTO(
                    HttpStatus.BAD_REQUEST.value(),
                    "Error al registrar el usuario: " + e.getMessage(),
                    usuario
            ));
        }
    }
}