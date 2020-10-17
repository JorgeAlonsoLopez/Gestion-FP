package com.salesianostriana.edu.proyecto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AlumnoController {

    @GetMapping("/alumno/principal")
    public String alumno() {

        return "alumno/principal";
    }


}
