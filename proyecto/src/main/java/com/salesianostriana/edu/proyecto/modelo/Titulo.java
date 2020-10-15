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
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "titulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curso> listaCursos = new ArrayList<>();

    public Titulo(String nombre) {
        this.nombre = nombre;
    }

    public void addCurso(Curso curso) {
        curso.setTitulo(this);
        this.listaCursos.add(curso);
    }

    public void deleteCurso(Curso curso) {
        this.listaCursos.remove(curso);
        curso.setTitulo(null);
    }

}
