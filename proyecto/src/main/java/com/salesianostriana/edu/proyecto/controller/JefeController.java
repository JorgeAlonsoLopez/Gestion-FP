package com.salesianostriana.edu.proyecto.controller;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.servicio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JefeController {

    private final ProfesorServicio profesorServicio;
    private final AlumnoServicio alumnoServicio;
    private final CursoServicio cursoServicio;




    @GetMapping("/jefe/principal")
    public String jefeEstudios(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/principal";
    }


    @GetMapping("/jefe/ampliacion")
    public String ampliacion(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/ampliacion";
    }


    @GetMapping("/jefe/aprobados")
    public String aprobados(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/aprobados";
    }


    @GetMapping("/jefe/asignaturas")
    public String asignaturas(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/asignaturas";
    }


    @GetMapping("/jefe/carnet")
    public String carnet(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/carnet";
    }


    @GetMapping("/jefe/clases")
    public String clases(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/clases";
    }


    @GetMapping("/jefe/cursos")
    public String cursos(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/cursos";
    }


    @GetMapping("/jefe/excepciones")
    public String excepciones(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/excepciones";
    }


    @GetMapping("/jefe/horarios")
    public String horarios(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/horarios";
    }


    @GetMapping("/jefe/nuevoAlumno")
    public String nuevoAlumno(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        Alumno alumnoForm = new Alumno();
        alumnoForm.setCodigoBienv(profesorServicio.codigo());
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("nuevoAlum", alumnoForm);
        model.addAttribute("cursos", cursoServicio.findAll());
        return "jefe/nuevoAlumno";
    }

    @PostMapping("/jefe/nuevoAlumno/submit")
    public String nuevoAlumnoSubmit(@ModelAttribute("nuevoAlum") Alumno alumnoForm) {
        if(profesorServicio.findByEmail(alumnoForm.getEmail()) == null && alumnoServicio.findByEmail(alumnoForm.getEmail()) == null){
            alumnoServicio.save(alumnoForm);
        }
        return "redirect:/jefe/principal";
    }


    @GetMapping("/jefe/nuevoProfesor")
    public String nuevoProfesor(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        Profesor profForm = new Profesor();
        profForm.setCodigoBienv(profesorServicio.codigo());
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("nuevoProf", profForm);
        return "jefe/nuevoProfesor";
    }

    @PostMapping("/jefe/nuevoProfesor/submit")
    public String nuevoProfesorSubmit(@ModelAttribute("nuevoProf") Profesor profForm) {
        profForm.setEsJefeDeEstudios(false);
        if(profesorServicio.findByEmail(profForm.getEmail()) == null && alumnoServicio.findByEmail(profForm.getEmail()) == null){
            profesorServicio.save(profForm);
        }
        return "redirect:/jefe/principal";
    }

    @GetMapping("/jefe/nuevoJefe")
    public String nuevoJefe(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        Profesor profForm = new Profesor();
        profForm.setCodigoBienv(profesorServicio.codigo());
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("nuevoJefe", profForm);
        return "jefe/nuevoJefe";
    }

    @PostMapping("/jefe/nuevoJefe/submit")
    public String nuevoJefeSubmit(@ModelAttribute("nuevoJefe") Profesor nuevoJefe) {
        nuevoJefe.setEsJefeDeEstudios(true);
        if(profesorServicio.findByEmail(nuevoJefe.getEmail()) == null && alumnoServicio.findByEmail(nuevoJefe.getEmail()) == null){
            profesorServicio.save(nuevoJefe);
        }
        return "redirect:/jefe/principal";
    }


    @GetMapping("/jefe/titulos")
    public String titulos(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/titulos";
    }

}
