package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.AmpliacionMatricula;
import com.salesianostriana.edu.proyecto.repositorio.AmpliacionMatriculaRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class AmpliacionMatriculaServicio extends BaseService<AmpliacionMatricula, Long, AmpliacionMatriculaRepository> {


    public AmpliacionMatriculaServicio(AmpliacionMatriculaRepository repo) {
        super(repo);
    }
}
