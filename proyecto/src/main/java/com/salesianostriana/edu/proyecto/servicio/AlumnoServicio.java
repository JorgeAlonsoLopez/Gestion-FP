package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.repositorio.AlumnoRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class AlumnoServicio extends BaseService<Alumno, Long, AlumnoRepository> {

    public AlumnoServicio(AlumnoRepository repo) {
        super(repo);
    }
}
