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




}
