package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.repositorio.AsignaturaRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class AsignaturaServicio extends BaseService<Asignatura, Long, AsignaturaRepository> {


    public AsignaturaServicio(AsignaturaRepository repo) {
        super(repo);
    }
}
