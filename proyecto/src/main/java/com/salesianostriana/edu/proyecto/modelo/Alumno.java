package com.salesianostriana.edu.proyecto.modelo;

import lombok.*;
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
public class Alumno extends Usuario{


    @ManyToOne
    private Curso curso;

    @ManyToMany
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ALUM"));
    }

}
