package com.salesianostriana.edu.proyecto.controller;

import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.modelo.Horario;
import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.servicio.AsignaturaServicio;
import com.salesianostriana.edu.proyecto.servicio.CursoServicio;
import com.salesianostriana.edu.proyecto.servicio.ProfesorServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorServicio profesorServicio;
    private final CursoServicio cursoServicio;

    @GetMapping("/profesor/principal")
    public String profesor(Model model, @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.findAll());


        return "profesor/principal";
    }

    @GetMapping("/profesor/cruso/{id}")
    public String curso(Model model, @AuthenticationPrincipal Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("curso", cursoServicio.findById(id));
        model.addAttribute("alumnos", cursoServicio.findById(id).getAlumnos());
        return "profesor/curso";
    }




}
