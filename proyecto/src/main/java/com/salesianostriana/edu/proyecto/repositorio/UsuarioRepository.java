package com.salesianostriana.edu.proyecto.repositorio;

import com.salesianostriana.edu.proyecto.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findFirstByEmail(String email);

}
