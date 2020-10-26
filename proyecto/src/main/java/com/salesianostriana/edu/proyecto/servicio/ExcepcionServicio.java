package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.repositorio.ExcepcionRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExcepcionServicio extends BaseService<Excepcion, ExcepcionPK, ExcepcionRepository> {
    @Query("Select e From Excepcion e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado='Aceptado' and e.tipo= :TIPO")
    public Optional<Excepcion> buscarExistenciaTerminadaExcepcionExc(Asignatura Asig, Alumno alumno, String tipo) {
        return repositorio.buscarExistenciaTerminadaExcepcionExc(Asig, alumno, tipo);
    }

    @Query("Select e From Excepcion e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado='Aceptado' and e.tipo= :TIPO")
    public Optional<Excepcion> buscarExistenciaTerminadaExcepcionConv(Asignatura Asig, Alumno alumno, String tipo) {
        return repositorio.buscarExistenciaTerminadaExcepcionConv(Asig, alumno, tipo);
    }

    public ExcepcionServicio(ExcepcionRepository repo) {
        super(repo);

    }

    public Optional<Excepcion> buscarExistenciaTerminadaExcepcion(Asignatura Asig, Alumno alumno) {
        return repositorio.buscarExistenciaTerminadaExcepcion(Asig, alumno);
    }



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
