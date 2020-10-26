package com.salesianostriana.edu.proyecto.modelo;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name="alumno_id"),
            inverseJoinColumns = @JoinColumn(name="asignatura_id")
    )
    private Set<Asignatura> asignaturas = new HashSet<>();

    public void addAsignatura(Asignatura a) {
        asignaturas.add(a);
        a.getAlumnos().add(this);
    }

    public void removeAsignatura(Asignatura a) {
        asignaturas.remove(a);
        a.getAlumnos().remove(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ALUM"));
    }

    public Alumno(String email, String nombre, String apellidos, Curso curso) {
        super(email, nombre, apellidos);
        this.curso = curso;
    }

    public Alumno(String email, boolean primerInic, String codigoBienv, String nombre, String apellidos, boolean esAlta, Curso curso) {
        super(email, primerInic, codigoBienv, nombre, apellidos, esAlta);
        this.curso = curso;
    }

    public Alumno(String email, String contrasenya, boolean primerInic, String nombre, String apellidos, boolean esAlta, Curso curso) {
        super(email, contrasenya, primerInic, nombre, apellidos, esAlta);
        this.curso = curso;
    }
}
