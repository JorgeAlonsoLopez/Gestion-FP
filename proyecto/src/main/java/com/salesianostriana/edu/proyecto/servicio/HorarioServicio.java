package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Horario;
import com.salesianostriana.edu.proyecto.repositorio.HorarioRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioServicio extends BaseService<Horario, Long, HorarioRepository> {

    private final AsignaturaServicio asignaturaServicio;

    public HorarioServicio(HorarioRepository repo, AsignaturaServicio asignaturaServicio) {
        super(repo);
        this.asignaturaServicio = asignaturaServicio;
    }

    public void cargarListado() {
        List<Horario> result = new ArrayList<>();

        String path = "classpath:Horario.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");
                return new Horario(Integer.parseInt(values[2]), Integer.parseInt(values[3]),
                        asignaturaServicio.findByNameCurs(values[0],values[1]), true);

            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de horarios.");
            System.exit(-1);
        }

        for(Horario h : result){
            h.getAsigntura().addHorario(h);
            this.save(h);
        }

    }




}
