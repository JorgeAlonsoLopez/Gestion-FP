package com.salesianostriana.edu.proyecto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfesorController {

    @GetMapping("/profesor/principal")
    public String profesor() {

        return "profesor/principal";
    }


}
