package com.salesianostriana.edu.proyecto.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class ExcepcionPK implements Serializable {

    private static final long serialVersionUID = 8682909319466153524L;

    long alumno_id;

    long asignatura_id;


}
