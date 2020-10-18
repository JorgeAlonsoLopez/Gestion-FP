package com.salesianostriana.edu.proyecto.modelo;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Alumno extends Usuario{


    @ManyToOne
    private Curso curso;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name="alumno_id"),
            inverseJoinColumns = @JoinColumn(name="asignatura_id")
    )
    private List<Asignatura> asignaturas = new ArrayList<>();

    public void addAsignatura(Asignatura a) {
        asignaturas.add(a);
        a.getAlumnos().add(this);
    }

    public void removeAsignatura(Asignatura a) {
        asignaturas.remove(a);
        a.getAlumnos().remove(this);
    }


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "alumno")
    private List<Excepcion> listaExcepciones = new ArrayList<>();

    public void addExcepcion(Excepcion e) {
        listaExcepciones.add(e);
        e.setAlumno(this);
    }

    public void removeExcepcion(Excepcion e) {
        listaExcepciones.remove(e);
        e.setAlumno(null);
    }


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "alumno")
    private List<Ampliacion> listaAmpliaciones = new ArrayList<>();

    public void addAmpliacion(Ampliacion e) {
        listaAmpliaciones.add(e);
        e.setAlumno(this);
    }

    public void removeAmpliacion(Ampliacion e) {
        listaAmpliaciones.remove(e);
        e.setAlumno(null);
    }



    public Alumno(String email, String contrasenya, boolean primerInic, String nombre, String apellidos, Curso curso) {
        super(email, contrasenya, primerInic, nombre, apellidos);
        this.curso = curso;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ALUM"));
    }

    public Alumno(String email, String nombre, String apellidos, Curso curso) {
        super(email, nombre, apellidos);
        this.curso = curso;
    }
}
