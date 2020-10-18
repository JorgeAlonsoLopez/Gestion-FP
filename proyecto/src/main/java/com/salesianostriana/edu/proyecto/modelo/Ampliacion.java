package com.salesianostriana.edu.proyecto.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Ampliacion {

    @EmbeddedId
    private AmpliacionPK id = new AmpliacionPK();

    @ManyToOne
    @MapsId("alumno_id")
    @JoinColumn(name="alumno_id")
    private Alumno alumno;


    @ManyToOne
    @MapsId("asignatura_id")
    @JoinColumn(name="asignatura_id")
    private Asignatura asignatura;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaSolicitud;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaResolucion;

    private String estado;


    public Ampliacion(Alumno alumno, Asignatura asignatura, LocalDate fechaSolicitud, String estado) {
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    public Ampliacion(AmpliacionPK id, Alumno alumno, Asignatura asignatura, LocalDate fechaSolicitud, String estado) {
        this.id = id;
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }
}
