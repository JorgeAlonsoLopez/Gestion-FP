package com.salesianostriana.edu.proyecto.controller;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.servicio.AlumnoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoServicio alumnoServicio;

    @GetMapping("/alumno/principal")
    public String principal(Model model, @AuthenticationPrincipal Alumno usuarioLog) {
        model.addAttribute("usuarioLogeado", alumnoServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("horarios", alumnoServicio.findByEmail(usuarioLog.getEmail())
        .getCurso().getAsignaturas());
        return "alumno/principal";
    }

    @GetMapping("/alumno/excepcion")
    public String excepcion(Model model, @AuthenticationPrincipal Alumno usuarioLog) {
        model.addAttribute("usuarioLogeado", alumnoServicio.findByEmail(usuarioLog.getEmail()));

        return "alumno/excepcion";
    }

    @GetMapping("/alumno/ampliacion")
    public String ampliacion(Model model, @AuthenticationPrincipal Alumno usuarioLog) {
        model.addAttribute("usuarioLogeado", alumnoServicio.findByEmail(usuarioLog.getEmail()));

        return "alumno/ampliacion";
    }


}
