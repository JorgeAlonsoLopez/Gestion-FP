package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.SituacionExcepcional;
import com.salesianostriana.edu.proyecto.repositorio.SituacionExepcionalRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class SituacionExcepcionalServicio extends BaseService<SituacionExcepcional, Long, SituacionExepcionalRepository> {


    public SituacionExcepcionalServicio(SituacionExepcionalRepository repo) {
        super(repo);
    }
}
