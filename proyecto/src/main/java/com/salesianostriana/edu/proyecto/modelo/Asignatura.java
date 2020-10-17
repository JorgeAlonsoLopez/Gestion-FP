package com.salesianostriana.edu.proyecto.modelo;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private boolean esAlta;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy="asignaturas")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Alumno> alumnos = new ArrayList<>();

    @ManyToOne
    private Profesor profesor;

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
