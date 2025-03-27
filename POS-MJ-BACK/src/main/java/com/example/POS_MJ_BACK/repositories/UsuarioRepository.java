package com.example.POS_MJ_BACK.repositories;

import com.example.POS_MJ_BACK.models.Usuario;
import com.example.POS_MJ_BACK.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);

    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findAllByRol(Rol rol);
}
