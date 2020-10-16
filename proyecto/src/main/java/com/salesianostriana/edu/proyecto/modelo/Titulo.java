package com.salesianostriana.edu.proyecto.modelo;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private boolean esAlta;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "titulo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Curso> listaCursos = new ArrayList<>();

    public Titulo(String nombre,  boolean esAlta) {
        this.nombre = nombre;
        this.esAlta = esAlta;
    }

    public void addCurso(Curso curso) {
        this.listaCursos.add(curso);
        curso.setTitulo(this);
    }

    public void deleteCurso(Curso curso) {
        this.listaCursos.remove(curso);
        curso.setTitulo(null);
    }

}
