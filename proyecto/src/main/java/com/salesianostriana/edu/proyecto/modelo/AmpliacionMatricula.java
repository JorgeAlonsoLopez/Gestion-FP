package com.salesianostriana.edu.proyecto.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class
AmpliacionMatricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Estado estado;
    private LocalDate fechaSolicitud;
    private LocalDate fechaResolucion;
    private Alumno alumno;

    @OneToMany
    private List<Asignatura> listaAsignaturas = new ArrayList<>();


    public enum Estado{
        PENDIENTE, CONFIRMADA, DENEGADA;
    }





}
