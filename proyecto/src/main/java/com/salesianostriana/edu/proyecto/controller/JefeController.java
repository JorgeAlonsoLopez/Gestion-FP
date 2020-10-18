package com.salesianostriana.edu.proyecto.controller;

import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.servicio.ProfesorServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class JefeController {

    private final ProfesorServicio profesorServicio;

    @GetMapping("/jefe/principal")
    public String jefeEstudios(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        return "jefe/principal";
    }


}
