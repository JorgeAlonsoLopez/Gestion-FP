package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.repositorio.AlumnoRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoServicio extends BaseService<Alumno, Long, AlumnoRepository> {

    public AlumnoServicio(AlumnoRepository repo) {
        super(repo);
    }

    public Alumno findByEmail(String email){

        Alumno alu = new Alumno();
        alu=null;

        for (Alumno a : this.findAll()){
            if(a.getEmail().equals(email)){
                alu = a;
            }
        }
        return alu;
    }

    public void cargarListado(CursoServicio curso, BCryptPasswordEncoder passwordEncoder) {
        List<Alumno> result = new ArrayList<>();

        String path = "classpath:Alumnos.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(",");
                return new Alumno(values[2], values[3], false, values[0], values[1], curso.findByName(values[4]));


            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de alumnos.");
            System.exit(-1);
        }

        for (Alumno a : result) {
            a.setContrasenya(passwordEncoder.encode(a.getContrasenya()));
            a.getCurso().addAlumno(a);
            this.save(a);
        }
    }

    public void cargarListadoAsignaturas(AsignaturaServicio asign) {

        for (Alumno a : this.findAll()) {
            for(Asignatura asig : a.getCurso().getAsignaturas()){
                a.addAsignatura(asig);
                asign.edit(asig);
            }
            this.edit(a);
        }
    }




}
