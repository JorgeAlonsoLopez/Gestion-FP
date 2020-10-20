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
public class Horario implements Comparable<Horario>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int dia;
    private int tramo;
    private boolean esAlta;

    @ManyToOne
    private Asignatura asignatura;

    public Horario(int dia, int tramo, Asignatura asignatura,  boolean esAlta) {
        this.dia = dia;
        this.tramo = tramo;
        this.asignatura = asignatura;
        this.esAlta = esAlta;
    }

    @Override
    public int compareTo(Horario o) {
        int numP=1, numN=-1;
        if(this.getTramo() < o.getTramo()) {
            return numP;
        }else{
            return numN;
        }

    }
}
