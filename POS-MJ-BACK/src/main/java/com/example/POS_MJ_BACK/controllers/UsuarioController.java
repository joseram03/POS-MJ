package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.models.Usuario;
import com.example.POS_MJ_BACK.services.RolService;
import com.example.POS_MJ_BACK.services.UsuarioService;
import com.example.POS_MJ_BACK.dto.RespuestaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<RespuestaDTO> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(new RespuestaDTO(200, "Usuarios obtenidos exitosamente", usuarios));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<RespuestaDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            return ResponseEntity.ok(new RespuestaDTO(200, "Usuario obtenido exitosamente", usuario));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO(500, e.getMessage(), null));
        }
    }


    // Actualizar usuario completo
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<RespuestaDTO> actualizarUsuarioCompleto(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            usuario.setRol(rolService.obtenerRol(usuario.getRol()));
            usuarioService.actualizarUsuarioCompleto(id, usuario);
            return ResponseEntity.ok(new RespuestaDTO(200, "Usuario actualizado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RespuestaDTO(400, "Error al actualizar el usuario: " + e.getMessage(), null));
        }
    }

    // Actualizar usuario Parcial
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<RespuestaDTO> actualizarUsuarioParcial(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            if (usuario.getRol() != null) {
                usuario.setRol(rolService.obtenerRol(usuario.getRol()));
            }
            Usuario usuarioActualizado = usuarioService.actualizarUsuarioParcial(id, usuario);
            return ResponseEntity.ok(new RespuestaDTO(200, "Usuario actualizado exitosamente", usuarioActualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RespuestaDTO(400, "Error al actualizar el usuario: " + e.getMessage(), null));
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<RespuestaDTO> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok(new RespuestaDTO(200, "Usuario eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RespuestaDTO(400, "Error al eliminar el usuario: " + e.getMessage(), null));
        }
    }

    //Obtener usuario por nombre
    @GetMapping("/nombre/{nombre}")
    @PreAuthorize("hasAnyRole('VENDEDOR'||'ADMINISTRADOR')")
    public ResponseEntity<RespuestaDTO> obtenerUsuarioPorNombre(@PathVariable String nombre) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorNombre(nombre);
            return ResponseEntity.ok(new RespuestaDTO(200, "Usuario obtenido exitosamente", usuario));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO(404, "Usuario no encontrado", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO(500, "Error al obtener el usuario: " + e.getMessage(), null));
        }
    }

}
