package com.salesianostriana.edu.proyecto.controller;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.servicio.AlumnoServicio;
import com.salesianostriana.edu.proyecto.servicio.HorarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoServicio alumnoServicio;
    private final HorarioServicio horarioServicio;

    @GetMapping("/alumno/principal")
    public String principal(Model model, @AuthenticationPrincipal Alumno usuarioLog) {
        Alumno alum = alumnoServicio.findByEmail(usuarioLog.getEmail());
        model.addAttribute("usuarioLogeado", alum);
        model.addAttribute("horarios", horarioServicio.ordenarFinal(horarioServicio.findActivasByCurso(alum.getCurso())));
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
