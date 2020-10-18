package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.repositorio.AmpliacionRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AmpliacionServicio extends BaseService<Ampliacion, AmpliacionPK, AmpliacionRepository> {

    private final AlumnoServicio alumnoServicio;
    private final AsignaturaServicio asignaturaServicio;


    public AmpliacionServicio(AmpliacionRepository repo, AlumnoServicio alumnoServicio, AsignaturaServicio asignaturaServicio) {
        super(repo);
        this.alumnoServicio = alumnoServicio;
        this.asignaturaServicio = asignaturaServicio;
    }

    public void nuevaAmpliacion (String correo, String curso, String nombreAsignatura){
        Alumno alum = alumnoServicio.findByEmail(correo);
        Asignatura asign = asignaturaServicio.findByNameCurs(nombreAsignatura, curso);

        AmpliacionPK pk = new AmpliacionPK();
        pk.setAlumno_id(alum.getId());
        pk.setAsignatura_id(asign.getId());

//        Ampliacion ampl = this.findById(pk);

        Ampliacion ampl = new Ampliacion(pk, alum, asign, LocalDate.now(), "Pendiente");

//        ampl.setAlumno(alum);
//        ampl.setAsignatura(asign);
//        ampl.setFechaSolicitud(LocalDate.now());
//        ampl.setEstado("Pendiente");

        alum.addAmpliacion(ampl);
        asign.addAmpliacion(ampl);
        alumnoServicio.edit(alum);
        asignaturaServicio.edit(asign);
        this.save(ampl);

    }

    public void aceptarExcepcion(Ampliacion ampli){

        Alumno alum = ampli.getAlumno();

        ampli.setEstado("Aceptado");
        ampli.setFechaResolucion(LocalDate.now());

        alum.addAsignatura(ampli.getAsignatura());

        alumnoServicio.edit(alum);
        asignaturaServicio.edit(ampli.getAsignatura());
        this.edit(ampli);

    }

    public void declinarExcepcion(Ampliacion ampli){

        Alumno alum = ampli.getAlumno();

        ampli.setEstado("Rechazado");
        ampli.setFechaResolucion(LocalDate.now());

        this.edit(ampli);

    }



}
