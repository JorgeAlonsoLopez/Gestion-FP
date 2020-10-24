package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.repositorio.ExcepcionRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcepcionServicio extends BaseService<Excepcion, ExcepcionPK, ExcepcionRepository> {
    public ExcepcionServicio(ExcepcionRepository repo, AlumnoServicio alumnoServicio, AsignaturaServicio asignaturaServicio) {
        super(repo);
        this.alumnoServicio = alumnoServicio;
        this.asignaturaServicio = asignaturaServicio;
    }

    private final AlumnoServicio alumnoServicio;
    private final AsignaturaServicio asignaturaServicio;

    public Excepcion buscarPorId(Long idAlum, Long idAsig){
        Excepcion excepcion = new Excepcion();
        for(Excepcion exc : this.findAll()){
            if(idAlum==exc.getAlumno().getId()){
                if(idAsig==exc.getAsignatura().getId()){
                    excepcion = exc;
                }
            }
        }
        return excepcion;
    }

    public List<Excepcion> listarPorAlumno(Alumno al){
        List<Excepcion> lista = new ArrayList<>();
        for(Excepcion exc : this.findAll()){
            if(al.getId()==exc.getAlumno().getId()){
                    lista.add(exc);
            }
        }
        return lista;
    }

    public void aceptarExcepcion(Excepcion excep){
        excep.setEstado("Aceptado");
        excep.setFechaResolucion(LocalDate.now());
        this.edit(excep);

    }

    public void declinarExcepcion(Excepcion excep){
        excep.setEstado("Rechazado");
        excep.setFechaResolucion(LocalDate.now());
        this.edit(excep);

    }




}
