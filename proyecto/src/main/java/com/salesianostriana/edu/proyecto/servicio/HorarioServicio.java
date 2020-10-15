package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Asignatura;
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

    public HorarioServicio(HorarioRepository repo) {
        super(repo);
    }

    public AsignaturaServicio asignatura;

    public void cargarListado() {
        List<Horario> result = new ArrayList<>();

        String path = "classpath:Horarios.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");
                return new Horario(Integer.parseInt(values[0]), Integer.parseInt(values[1]), asignatura.findByNameCurs(values[0],values[0]));

            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de horarios.");
            System.exit(-1);
        }

        for(Horario h : result){
            this.save(h);
        }

    }




}
