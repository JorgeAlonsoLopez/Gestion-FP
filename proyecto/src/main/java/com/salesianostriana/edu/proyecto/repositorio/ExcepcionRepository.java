package com.salesianostriana.edu.proyecto.repositorio;

import com.salesianostriana.edu.proyecto.modelo.Excepcion;
import com.salesianostriana.edu.proyecto.modelo.ExcepcionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcepcionRepository extends JpaRepository<Excepcion, ExcepcionPK> {



}
