package com.salesianostriana.edu.proyecto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class JefeController {

    @GetMapping("/jefe/principal")
    public String jefeEstudios() {

        return "jefe/principal";
    }


}
