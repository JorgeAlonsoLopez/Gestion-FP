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
@AllArgsConstructor
public class Profesor extends Usuario{

    private boolean esJefeDeEstudios;

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

    public Profesor(String email, String nombre, String apellidos, boolean esJefeDeEstudios) {
        super(email, nombre, apellidos);
        this.esJefeDeEstudios = esJefeDeEstudios;
    }



}
