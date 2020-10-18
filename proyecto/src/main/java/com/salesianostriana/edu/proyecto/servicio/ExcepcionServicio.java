package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.repositorio.ExcepcionRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExcepcionServicio extends BaseService<Excepcion, ExcepcionPK, ExcepcionRepository> {
    public ExcepcionServicio(ExcepcionRepository repo, AlumnoServicio alumnoServicio, AsignaturaServicio asignaturaServicio) {
        super(repo);
        this.alumnoServicio = alumnoServicio;
        this.asignaturaServicio = asignaturaServicio;
    }

    private final AlumnoServicio alumnoServicio;
    private final AsignaturaServicio asignaturaServicio;

    public void aceptarExcepcion(Excepcion excep){

        Alumno alum = excep.getAlumno();

        excep.setEstado("Aceptado");
        excep.setFechaResolucion(LocalDate.now());

        alum.removeAsignatura(excep.getAsignatura());

        alumnoServicio.edit(alum);
        asignaturaServicio.edit(excep.getAsignatura());
        this.edit(excep);

    }

    public void declinarExcepcion(Excepcion excep){

        Alumno alum = excep.getAlumno();

        excep.setEstado("Rechazado");
        excep.setFechaResolucion(LocalDate.now());

        this.edit(excep);

    }

    public void nuevaExcepcion (String correo, String curso, String nombreAsignatura){

        Alumno alum = alumnoServicio.findByEmail(correo);
        Asignatura asign = asignaturaServicio.findByNameCurs(nombreAsignatura, curso);

        ExcepcionPK pk = new ExcepcionPK();
        pk.setAlumno_id(alum.getId());
        pk.setAsignatura_id(asign.getId());

//         Excepcion exp = this.findById(pk);

        Excepcion exp = new Excepcion();
//        Excepcion exp = new Excepcion(pk, alum, asign, LocalDate.now(), "Convalidacion", "Pendiente");

        exp.setId(pk);
        exp.setAlumno(alum);
        exp.setAsignatura(asign);
        exp.setFechaSolicitud(LocalDate.now());
        exp.setTipo("Convalidacion");
        exp.setEstado("Pendiente");

        alum.addExcepcion(exp);
        asign.addExcepcion(exp);
        alumnoServicio.edit(alum);
        asignaturaServicio.edit(asign);
        this.save(exp);

    }


}
