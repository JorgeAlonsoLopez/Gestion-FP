package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.repositorio.ProfesorRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ProfesorServicio extends BaseService<Profesor, Long, ProfesorRepository> {


    public ProfesorServicio(ProfesorRepository repo) {
        super(repo);
    }
}
