package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.models.Usuario;
import com.example.POS_MJ_BACK.services.UsuarioService;
import com.example.POS_MJ_BACK.services.RolService;
import com.example.POS_MJ_BACK.models.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder passwordEncoder;

    // Registro de usuario
    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            // Asignar un rol 'vendedor' por defecto
            Optional<Rol> rol = rolService.obtenerTodosLosRoles().stream()
                    .filter(r -> r.getNombre().equals("vendedor"))
                    .findFirst();

            if (rol.isPresent()) {
                usuario.setRol(rol.get());
                usuarioService.crearUsuario(usuario);
                return ResponseEntity.ok("Usuario registrado exitosamente.");
            } else {
                return ResponseEntity.status(400).body("Rol no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // Inicio de sesión (Ejemplo simple sin seguridad completa)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioExistente = usuarioService.obtenerTodosLosUsuarios().stream()
                    .filter(u -> u.getUsuario().equals(usuario.getUsuario()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

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

    // Obtener todos los usuarios (solo para administradores)
    @GetMapping("/usuarios")
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    // Obtener usuario por ID (solo para administradores)
    @GetMapping("/usuarios/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    // Actualizar usuario
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            usuarioService.actualizarUsuario(id, usuario);
            return ResponseEntity.ok("Usuario actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    // Eliminar usuario
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
