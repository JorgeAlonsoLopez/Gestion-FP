package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Horario;
import com.salesianostriana.edu.proyecto.repositorio.HorarioRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class HorarioServicio extends BaseService<Horario, Long, HorarioRepository> {


    public HorarioServicio(HorarioRepository repo) {
        super(repo);
    }
}
