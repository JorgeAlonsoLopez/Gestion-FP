package com.salesianostriana.edu.proyecto.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy="asignaturas")
    private List<Alumno> alumnos = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy="asignaturas")
    private List<Profesor> profesores = new ArrayList<>();

    @ManyToOne
    private Curso curso;

    public Asignatura(String nombre, Curso curso) {
        this.nombre = nombre;
        this.curso = curso;
    }
}
