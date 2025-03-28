package com.example.POS_MJ_BACK.controllers;

import com.example.POS_MJ_BACK.models.Usuario;
import com.example.POS_MJ_BACK.services.RolService;
import com.example.POS_MJ_BACK.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping() //Tested
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    // Obtener usuario por ID (solo para administradores implementar)
    @GetMapping("/{id}") //Tested
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    // Actualizar usuario completo
    // Requisito: nombre, usuario, correo, contrasenha, rol (si no especifica es "vendedor")
    @PutMapping("/{id}") //Tested
    public ResponseEntity<String> actualizarUsuarioCompleto(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            // Como el Rol podemos optener parcialmente, recuperamos completo
            usuario.setRol(rolService.obtenerRol(usuario.getRol()));
            usuarioService.actualizarUsuarioCompleto(id, usuario);
            return ResponseEntity.ok("Usuario actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    // Actualizar usuario Parcial
    // Requisito: solo atributos a cambiar, el rol puede ser parcial
    @PatchMapping("/{id}") //Tested
    public ResponseEntity<String> actualizarUsuarioParcial(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            // Recuperamos el Rol si es que mandaron
            if(usuario.getRol() != null){
                usuario.setRol(rolService.obtenerRol(usuario.getRol()));
            }
            usuarioService.actualizarUsuarioParcial(id, usuario);
            return ResponseEntity.ok("Usuario actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}") //Tested
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
