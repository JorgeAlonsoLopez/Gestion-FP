package com.salesianostriana.edu.proyecto.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruta;
    private Long idEntidad;
    private boolean guardar;

    public Store(String ruta, boolean guardar) {
        this.ruta = ruta;
        this.guardar = guardar;
    }
}
