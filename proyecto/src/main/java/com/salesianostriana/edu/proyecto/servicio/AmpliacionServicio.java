package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.repositorio.AmpliacionRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmpliacionServicio extends BaseService<Ampliacion, AmpliacionPK, AmpliacionRepository> {


    private final HorarioServicio horarioServicio;
    private final ExcepcionServicio excepcionServicio;


    public AmpliacionServicio(AmpliacionRepository repo, HorarioServicio horarioServicio, ExcepcionServicio excepcionServicio) {
        super(repo);

        this.horarioServicio = horarioServicio;
        this.excepcionServicio = excepcionServicio;
    }

    public Ampliacion buscarPorId(Long idAlum, Long idAsig){
        Ampliacion ampliacion = new Ampliacion();
        for(Ampliacion amp : this.findAll()){
            if(idAlum==amp.getAlumno().getId()){
                if(idAsig==amp.getAsignatura().getId()){
                    ampliacion = amp;
                }
            }
        }
        return ampliacion;
    }

    public List<Ampliacion> listarPorAlumno(Alumno al){
        List<Ampliacion> lista = new ArrayList<>();
        for(Ampliacion amp : this.findAll()){
            if(al.getId()==amp.getAlumno().getId()){
                lista.add(amp);
            }
        }
        return lista;
    }

    public void aceptarAmpliacion(Ampliacion ampli){
        ampli.setEstado("Aceptado");
        ampli.setFechaResolucion(LocalDate.now());
        this.edit(ampli);

    }

    public void declinarAmpliacion(Ampliacion ampli){
        ampli.setEstado("Rechazado");
        ampli.setFechaResolucion(LocalDate.now());
        this.edit(ampli);

    }

    public boolean comprobarAmpliacion(Alumno alum, Asignatura asignatura){
        boolean aceptar = true;
        List<Horario> listaHorarios;
        listaHorarios = horarioServicio.horariosTest(alum, excepcionServicio.findAll());
        for(Horario horA : asignatura.getHorarios()){
            for(Horario horT : listaHorarios){
                if(horT.getTramo()==horA.getTramo() && horT.getDia()==horA.getDia()){
                    aceptar = false;
                }
            }
        }


        return aceptar;
    }



}
