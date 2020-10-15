package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Titulo;
import com.salesianostriana.edu.proyecto.repositorio.TituloRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class TituloServicio extends BaseService<Titulo, Long, TituloRepository> {


    public TituloServicio(TituloRepository repo) {
        super(repo);
    }
}
