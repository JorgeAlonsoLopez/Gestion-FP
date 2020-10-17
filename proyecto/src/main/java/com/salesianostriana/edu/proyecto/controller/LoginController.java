package com.salesianostriana.edu.proyecto.controller;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.modelo.Usuario;
import com.salesianostriana.edu.proyecto.servicio.AlumnoServicio;
import com.salesianostriana.edu.proyecto.servicio.ProfesorServicio;
import com.salesianostriana.edu.proyecto.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AlumnoServicio alumnoServicio;
    private final ProfesorServicio profesorServicio;
    private final UsuarioServicio usuarioServicio;

    @GetMapping({"/", "/inicio"})
    public String index(){
        return "inicio";
    }

    @GetMapping("/invitacionA")
    public String invitadoA(Model model){
        model.addAttribute("alumno", new Alumno());
        return "invitacionA";
    }

    @PostMapping("/invitacionA/submit")
    public String confirmacionPasswA(@ModelAttribute("alumno") Alumno alumno, BCryptPasswordEncoder passwordEncoder){
        Alumno alumno2 = alumnoServicio.findByEmail(alumno.getEmail());
        if(alumno.getCodigoBienv().equals(alumno2.getCodigoBienv()) && alumno2.isPrimerInic()==false){
            alumno2.setContrasenya(passwordEncoder.encode(alumno.getContrasenya()));
            alumnoServicio.edit(alumno2);
            return "redirect:/login";
        }else{
            return "redirect:/inicio";
        }
    }

    @GetMapping("/invitacionP")
    public String invitadoP(Model model){
        model.addAttribute("profesor", new Profesor());
        return "invitacionP";
    }

    @PostMapping("/invitacionP/submit")
    public String confirmacionPasswP(@ModelAttribute("profesor") Profesor profesor, BCryptPasswordEncoder passwordEncoder){
        Profesor profesor2 = profesorServicio.findByEmail(profesor.getEmail());
        if(profesor.getCodigoBienv().equals(profesor2.getCodigoBienv()) && profesor2.isPrimerInic()==false){
            profesor2.setContrasenya(passwordEncoder.encode(profesor.getContrasenya()));
            profesorServicio.edit(profesor2);
            return "redirect:/login";
        }else{
            return "redirect:/inicio";
        }
    }



}
