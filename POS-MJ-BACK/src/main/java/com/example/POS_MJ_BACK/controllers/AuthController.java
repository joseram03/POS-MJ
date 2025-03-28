package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.models.Usuario;
import com.example.POS_MJ_BACK.services.UsuarioService;
import com.example.POS_MJ_BACK.services.RolService;
import com.example.POS_MJ_BACK.models.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.webauthn.api.PublicKeyCose;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registro de usuario
    // Es requisito: nombre, usuario, correo, contrasenha
    @PostMapping("/registro") //Tested
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            // Asignar un rol 'vendedor' por defecto si no especifica que es 'administrador'
            usuario.setRol(rolService.obtenerRol(usuario.getRol()));
            usuarioService.crearUsuario(usuario);
            return ResponseEntity.ok("Usuario registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // Inicio de sesión (Ejemplo simple sin seguridad completa)
    // Solo valida usuario y contrasenha no deben ser null
    @PostMapping("/login") //Tested
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioService.obtenerUsuarioPorNombreUsuario(usuario.getUsuario());

            if (passwordEncoder.matches(usuario.getContrasenha(), usuarioExistente.getContrasenha())) {
                // Aquí podrías generar un JWT o iniciar una sesión
                return ResponseEntity.ok("Inicio de sesión exitoso.");
            } else {
                return ResponseEntity.status(401).body("Credenciales incorrectas.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al iniciar sesión: " + e.getMessage());
        }
    }
}
