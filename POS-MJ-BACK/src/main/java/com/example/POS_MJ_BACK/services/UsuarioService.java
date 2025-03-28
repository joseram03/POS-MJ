package com.example.POS_MJ_BACK.services;

import com.example.POS_MJ_BACK.models.Usuario;
import com.example.POS_MJ_BACK.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Para encriptar contraseñas

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }
        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        usuario.setContrasenha(passwordEncoder.encode(usuario.getContrasenha()));

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuarioParcial(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(usuarioActualizado.getNombre() != null) usuario.setNombre(usuarioActualizado.getNombre());
        if(usuarioActualizado.getUsuario() != null) usuario.setUsuario(usuarioActualizado.getUsuario());
        if(usuarioActualizado.getCorreo() != null) usuario.setCorreo(usuarioActualizado.getCorreo());

        if (usuarioActualizado.getContrasenha() != null) {
            usuario.setContrasenha(passwordEncoder.encode(usuarioActualizado.getContrasenha()));
        }

        if(usuarioActualizado.getRol() != null) usuario.setRol(usuarioActualizado.getRol());

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuarioCompleto(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setUsuario(usuarioActualizado.getUsuario());
        usuario.setCorreo(usuarioActualizado.getCorreo());

        if (usuarioActualizado.getContrasenha() != null) {
            usuario.setContrasenha(passwordEncoder.encode(usuarioActualizado.getContrasenha()));
        }

        usuario.setRol(usuarioActualizado.getRol());

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
