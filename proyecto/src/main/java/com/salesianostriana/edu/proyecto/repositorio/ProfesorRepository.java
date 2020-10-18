package com.salesianostriana.edu.proyecto.repositorio;

import com.salesianostriana.edu.proyecto.modelo.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
}
