package com.salesianostriana.edu.proyecto.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class SituacionExcepcional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Estado estado;
    private Tipo tipo;
    private LocalDate fechaSolicitud;
    private LocalDate fechaResolucion;
    private String adjunto;

    private Alumno alumno;

    @OneToMany
    private List<Asignatura> listaAsignaturas = new ArrayList<>();


    public enum Estado{
        PENDIENTE, CONFIRMADA, DENEGADA;
    }

    public enum Tipo{
        CONVALIDACION, EXCENCION;
    }




}
