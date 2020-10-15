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
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private int anyo;

    @ManyToOne
    private Titulo titulo;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "curso")
    private List<Asignatura> asignaturas = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="curso")
    private List<Alumno> alumnos = new ArrayList<>();

    public Curso(String nombre, int anyo, Titulo titulo) {
        this.nombre = nombre;
        this.anyo = anyo;
        this.titulo = titulo;
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
