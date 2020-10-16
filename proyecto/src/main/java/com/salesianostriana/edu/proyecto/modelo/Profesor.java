package com.salesianostriana.edu.proyecto.modelo;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
public class Profesor extends Usuario{

    private boolean esJefeDeEstudios;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name="profesor_id"),
            inverseJoinColumns = @JoinColumn(name="asignatura_id")
    )
    private List<Asignatura> asignaturas = new ArrayList<>();

    public void addAsignatura(Asignatura a) {
        asignaturas.add(a);
        a.getProfesores().add(this);
    }

    public void removeAsignatura(Asignatura a) {
        asignaturas.remove(a);
        a.getProfesores().remove(this);
    }

    public Profesor(String email, String contrasenya, boolean primerInic, String nombre, String apellidos, boolean esJefeDeEstudios) {
        super(email, contrasenya, primerInic, nombre, apellidos);
        this.esJefeDeEstudios = esJefeDeEstudios;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_";
        if (esJefeDeEstudios) {
            role += "JEFE";
        } else {
            role += "PROF";
        }
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

}
