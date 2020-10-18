package com.salesianostriana.edu.proyecto.repositorio;

import com.salesianostriana.edu.proyecto.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepositorio extends JpaRepository<Curso, Long> {
}
