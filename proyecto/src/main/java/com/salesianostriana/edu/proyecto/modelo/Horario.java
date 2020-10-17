package com.salesianostriana.edu.proyecto.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int dia;
    private int tramo;
    private boolean esAlta;

    @ManyToOne
    private Asignatura asigntura;

    public Horario(int dia, int tramo, Asignatura asigntura,  boolean esAlta) {
        this.dia = dia;
        this.tramo = tramo;
        this.asigntura = asigntura;
        this.esAlta = esAlta;
    }
}
