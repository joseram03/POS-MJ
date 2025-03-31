package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.models.Usuario;
import com.example.POS_MJ_BACK.services.RolService;
import com.example.POS_MJ_BACK.services.UsuarioService;
import com.example.POS_MJ_BACK.dto.RespuestaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    // Obtener todos los usuarios (solo para administradores)
    @GetMapping()
    public ResponseEntity<RespuestaDTO> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(new RespuestaDTO(200, "Usuarios obtenidos exitosamente", usuarios));
    }

    // Obtener usuario por ID (solo para administradores implementar)
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(new RespuestaDTO(200, "Usuario obtenido exitosamente", usuario));
    }

    // Actualizar usuario completo
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> actualizarUsuarioCompleto(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            usuario.setRol(rolService.obtenerRol(usuario.getRol()));
            usuarioService.actualizarUsuarioCompleto(id, usuario);
            return ResponseEntity.ok(new RespuestaDTO(200, "Usuario actualizado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RespuestaDTO(400, "Error al actualizar el usuario: " + e.getMessage()));
        }
    }

    // Actualizar usuario Parcial
    @PatchMapping("/{id}")
    public ResponseEntity<RespuestaDTO> actualizarUsuarioParcial(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            if (usuario.getRol() != null) {
                usuario.setRol(rolService.obtenerRol(usuario.getRol()));
            }
            Usuario usuarioActualizado = usuarioService.actualizarUsuarioParcial(id, usuario);
            return ResponseEntity.ok(new RespuestaDTO(200, "Usuario actualizado exitosamente", usuarioActualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RespuestaDTO(400, "Error al actualizar el usuario: " + e.getMessage()));
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok(new RespuestaDTO(200, "Usuario eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RespuestaDTO(400, "Error al eliminar el usuario: " + e.getMessage()));
        }
    }
}
