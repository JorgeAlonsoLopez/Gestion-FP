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

    @ManyToOne
    private Curso curso;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "asignatura", fetch = FetchType.EAGER)
    private List<Horario> horarios = new ArrayList<>();

    public void addHorario(Horario h) {
        horarios.add(h);
        h.setAsignatura(this);
    }

    public void removeHorario(Horario h) {
        horarios.remove(h);
        h.setAsignatura(null);
    }

    public Asignatura(String nombre, Curso curso,  boolean esAlta) {
        this.nombre = nombre;
        this.curso = curso;
        this.esAlta = esAlta;
    }



}
