package com.salesianostriana.edu.proyecto.controller;

import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.servicio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorServicio profesorServicio;
    private final CursoServicio cursoServicio;
    private final HorarioServicio horarioServicio;
    private final TituloServicio tituloServicio;

    @GetMapping("/profesor/principal")
    public String profesor(Model model, @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaActivos());
        return "profesor/principal";
    }

    @GetMapping("/profesor/horario/{id}")
    public String curso(Model model, @AuthenticationPrincipal Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("horarios", horarioServicio.ordenarFinal(horarioServicio.encontrarPorAsignaturasAltaDeCurso(cursoServicio.findById(id))));
        model.addAttribute("curso", cursoServicio.findById(id));
        return "profesor/horario";
    }


    @GetMapping("/profesor/cursos")
    public String cursos(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaDisponibles());
        return "profesor/cursos";
    }


    @GetMapping("/profesor/alumnos")
    public String alumnos(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaDisponibles());
        return "profesor/alumnos";
    }

    @GetMapping("/profesor/horarios")
    public String horarios(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaDisponibles());
        return "profesor/horarios";
    }

    @GetMapping("/profesor/titulos")
    public String titulos(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("titulos", tituloServicio.listarActivos());
        return "profesor/titulos";
    }

    @GetMapping("/profesor/clases")
    public String clases(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaDisponibles());
        return "profesor/clases";
    }

    @GetMapping("/profesor/clasesDetalles/{id}")
    public String clasesDetalles(Model model,  @AuthenticationPrincipal Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "profesor/clases";
    }


}
