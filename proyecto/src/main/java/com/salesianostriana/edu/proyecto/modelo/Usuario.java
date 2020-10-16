package com.salesianostriana.edu.proyecto.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements UserDetails {

    private static final long serialVersionUID = -6767914261998068491L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(unique = true)
    private String email;

    private String contrasenya;
    private String codigoBienv;
    private boolean primerInic;
    private String nombre;
    private String apellidos;

    public Usuario(String email, String codigoBienv, boolean primerInic, String nombre, String apellidos) {
        this.email = email;
        this.codigoBienv = codigoBienv;
        this.primerInic = primerInic;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return contrasenya;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
