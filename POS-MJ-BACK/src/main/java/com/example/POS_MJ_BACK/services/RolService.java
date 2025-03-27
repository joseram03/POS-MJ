package com.example.POS_MJ_BACK.services;

import com.example.POS_MJ_BACK.models.Rol;
import com.example.POS_MJ_BACK.repositories.RolRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @PostConstruct
    public void inicializarRoles() {
        crearRolSiNoExiste("administrador", "Rol con acceso total al sistema.");
        crearRolSiNoExiste("vendedor", "Rol con permisos para gestionar ventas.");
    }

    private void crearRolSiNoExiste(String nombre, String descripcion) {
        Optional<Rol> rolExistente = rolRepository.findByNombre(nombre);
        if (rolExistente.isEmpty()) {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre(nombre);
            nuevoRol.setDescripcion(descripcion);
            rolRepository.save(nuevoRol);
        }
    }

    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    public Rol obtenerRolPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    public Rol crearRol(Rol rol) {
        if (rolRepository.findByNombre(rol.getNombre()).isPresent()) {
            throw new RuntimeException("El rol ya existe.");
        }
        return rolRepository.save(rol);
    }

    public Rol actualizarRol(Long id, Rol rolActualizado) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rol.setNombre(rolActualizado.getNombre());
        rol.setDescripcion(rolActualizado.getDescripcion());

        return rolRepository.save(rol);
    }

    public void eliminarRol(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado");
        }
        rolRepository.deleteById(id);
    }
}
