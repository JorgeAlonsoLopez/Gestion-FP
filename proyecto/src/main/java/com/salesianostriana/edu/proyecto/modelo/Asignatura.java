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
    private boolean esAlta;

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

    @OneToMany(mappedBy = "asigntura", fetch = FetchType.EAGER)
    private List<Horario> horarios = new ArrayList<>();

    public Asignatura(String nombre, Curso curso,  boolean esAlta) {
        this.nombre = nombre;
        this.curso = curso;
        this.esAlta = esAlta;
    }

    public void addHorario(Horario h) {
        this.horarios.add(h);
        h.setAsigntura(this);
    }

    public void removeHorario(Horario h) {
        this.horarios.remove(h);
        h.setAsigntura(null);
    }




}
