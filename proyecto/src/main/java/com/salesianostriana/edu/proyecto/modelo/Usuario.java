package com.salesianostriana.edu.proyecto.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private boolean esAlta;

    public Usuario(String email, String contrasenya, boolean primerInic, String nombre, String apellidos, boolean esAlta) {
        this.email = email;
        this.contrasenya = contrasenya;
        this.primerInic = primerInic;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.esAlta = esAlta;
    }

    public Usuario(String email, String nombre, String apellidos) {
        this.email = email;
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
        if(this.isEsAlta()){
            return true;
        }else{
            return false;
        }

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
