package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.modelo.Horario;
import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.repositorio.ProfesorRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfesorServicio extends BaseService<Profesor, Long, ProfesorRepository> {

    public ProfesorServicio(ProfesorRepository repo) {
        super(repo);
    }

    public Profesor findByEmail(String email){

        Profesor prof = new Profesor();
        prof=null;

        for (Profesor t : this.findAll()){
            if(t.getEmail().equals(email)){
                prof = t;
            }
        }
        return prof;
    }

    public void cargarListadoProf_Asig(AsignaturaServicio asignatura) {


        String path = "classpath:Asignaturas-profesores.csv";
        try {
            // @formatter:off
            Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");

                this.findByEmail(values[2]).addAsignatura(asignatura.findByNameCurs(values[0], values[1]));
                this.edit(this.findByEmail(values[2]));

                return null;
            });
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de profesores.");
            System.exit(-1);
        }

    }

    public void cargarListado(AsignaturaServicio asignatura) {
        List<Profesor> result = new ArrayList<>();

        String path = "classpath:Profesores.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");
                if (values[4].equals("True")) {
                    return new Profesor(values[2], values[3], false,
                            values[0], values[1], true);
                } else {
                    return new Profesor(values[2], values[3], false,
                            values[0], values[1], false);
                }


            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de profesores.");
            System.exit(-1);
        }

        for(Profesor p : result){
            this.save(p);
        }

    }


}
