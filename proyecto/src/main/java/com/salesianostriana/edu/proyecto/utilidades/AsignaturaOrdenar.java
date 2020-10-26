package com.salesianostriana.edu.proyecto.utilidades;

import com.salesianostriana.edu.proyecto.modelo.Asignatura;

import java.util.Comparator;

public class AsignaturaOrdenar implements Comparator<Asignatura> {
    @Override
    public int compare(Asignatura o1, Asignatura o2) {
        return o1.getNombre().compareTo(o2.getNombre());
    }
}
