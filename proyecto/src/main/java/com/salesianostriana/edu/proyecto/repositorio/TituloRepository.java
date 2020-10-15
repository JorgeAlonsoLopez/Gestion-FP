package com.salesianostriana.edu.proyecto.repositorio;

import com.salesianostriana.edu.proyecto.modelo.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TituloRepository extends JpaRepository<Titulo, Long> {

}
