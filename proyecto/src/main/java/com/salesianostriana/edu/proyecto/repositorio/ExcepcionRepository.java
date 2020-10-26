package com.salesianostriana.edu.proyecto.repositorio;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.modelo.Excepcion;
import com.salesianostriana.edu.proyecto.modelo.ExcepcionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExcepcionRepository extends JpaRepository<Excepcion, ExcepcionPK> {

    @Query("Select e From Excepcion e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado='Aceptado'")
    public Optional<Excepcion> buscarExistenciaTerminadaExcepcion(@Param("ASIGNATURA") Asignatura Asig, @Param("AlUMNO") Alumno alumno);

    @Query("Select e From Excepcion e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado='Aceptado' and e.tipo= :TIPO")
    public Optional<Excepcion> buscarExistenciaTerminadaExcepcionExc(@Param("ASIGNATURA") Asignatura Asig, @Param("AlUMNO") Alumno alumno, @Param("TIPO") String tipo);

    @Query("Select e From Excepcion e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado='Aceptado' and e.tipo= :TIPO")
    public Optional<Excepcion> buscarExistenciaTerminadaExcepcionConv(@Param("ASIGNATURA") Asignatura Asig, @Param("AlUMNO") Alumno alumno, @Param("TIPO") String tipo);

}
