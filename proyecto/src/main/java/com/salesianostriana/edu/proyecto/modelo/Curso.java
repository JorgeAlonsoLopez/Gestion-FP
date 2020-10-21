package com.salesianostriana.edu.proyecto.modelo;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private int anyo;
    private boolean esAlta;

    @ManyToOne
    private Titulo titulo;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "curso", fetch = FetchType.EAGER)
    private Set<Asignatura> asignaturas = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="curso")
    private List<Alumno> alumnos = new ArrayList<>();

    public Curso(String nombre, int anyo, Titulo titulo,  boolean esAlta) {
        this.nombre = nombre;
        this.anyo = anyo;
        this.titulo = titulo;
        this.esAlta = esAlta;
    }

    public void addAlumno(Alumno a) {
        this.alumnos.add(a);
        a.setCurso(this);
    }

    public void removeAlumno(Alumno a) {
        this.alumnos.remove(a);
        a.setCurso(null);
    }

    public void addAsignatura(Asignatura a) {
        this.asignaturas.add(a);
        a.setCurso(this);
    }

    public void removeAsignatura(Asignatura a) {
        this.asignaturas.remove(a);
        a.setCurso(null);
    }

}
