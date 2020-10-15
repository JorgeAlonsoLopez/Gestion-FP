package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.modelo.Curso;
import com.salesianostriana.edu.proyecto.repositorio.AsignaturaRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignaturaServicio extends BaseService<Asignatura, Long, AsignaturaRepository> {

    public AsignaturaServicio(AsignaturaRepository repo) {
        super(repo);
    }

    public CursoServicio curso;

    public Asignatura findByNameCurs(String nombre, String curso){

        Asignatura asign = new Asignatura();
        asign=null;

        for (Asignatura t : this.findAll()){
            if(t.getNombre().equals(nombre)){
                if(t.getCurso().getNombre().equals(curso)){
                    asign = t;
                }
            }
        }
        return asign;
    }

    public void cargarListado() {
        List<Asignatura> result = new ArrayList<>();

        String path = "classpath:Asignaturas.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");

                    return new Asignatura(values[0], curso.findByName(values[1]));

            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de asignaturas.");
            System.exit(-1);
        }

        for(Asignatura a : result){
            a.getCurso().addAsignatura(a);
            curso.edit(a.getCurso());
            this.save(a);
        }

    }


}
