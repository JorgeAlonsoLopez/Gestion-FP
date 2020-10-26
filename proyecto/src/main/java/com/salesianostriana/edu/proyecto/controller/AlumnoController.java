package com.salesianostriana.edu.proyecto.controller;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.servicio.*;
import com.salesianostriana.edu.proyecto.upload.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoServicio alumnoServicio;
    private final HorarioServicio horarioServicio;
    private final AsignaturaServicio asignaturaServicio;
    private final ExcepcionServicio excepcionServicio;
    private final AmpliacionServicio ampliacionServicio;
    private final CursoServicio cursoServicio;
    private final StorageService storageService;

    @GetMapping("/alumno/principal")
    public String principal(Model model, @AuthenticationPrincipal Alumno usuarioLog) {
        Alumno alum = alumnoServicio.findByEmail(usuarioLog.getEmail());
        model.addAttribute("usuarioLogeado", alum);
        model.addAttribute("horarios", horarioServicio.ordenarFinal(horarioServicio.horariosPorAlumno(alum, ampliacionServicio.findAll())));
        return "alumno/principal";
    }

    @GetMapping("/alumno/excepcion")
    public String excepcion(Model model, @AuthenticationPrincipal Alumno usuarioLog) {
        Alumno alum = alumnoServicio.findByEmail(usuarioLog.getEmail());
        model.addAttribute("usuarioLogeado", alum);
        model.addAttribute("asignaturas", asignaturaServicio.findActivasPorCurso(alum.getCurso()));
        model.addAttribute("excepcionForm", new Excepcion());
        return "alumno/excepcion";
    }

    @PostMapping("/alumno/excepcion/submit")
    public String excepcionFrom(@ModelAttribute("excepcionForm") Excepcion excepcion, @AuthenticationPrincipal Alumno usuarioLog, @RequestParam("file") MultipartFile file) {
        String ruta = "";
        if(!file.isEmpty()) {
            String tipo = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 3);
            if (tipo.equals("pdf")) {
                String archivo = storageService.store(file, -1);
                ruta = MvcUriComponentsBuilder.fromMethodName(AlumnoController.class,"serveFile", archivo).build().toUriString();
            }
        }
        ExcepcionPK pk = new ExcepcionPK(usuarioLog.getId(), excepcion.getAsignatura().getId());
        excepcion.setAlumno(usuarioLog);
        excepcion.setId(pk);
        excepcion.setEstado("Pendiente");
        excepcion.setAdjunto(file.getOriginalFilename());
        excepcion.setFechaSolicitud(LocalDate.now());
        excepcionServicio.save(excepcion);
        return "redirect:/alumno/principal";
    }

    @GetMapping("/alumno/ampliacion")
    public String ampliacion(Model model, @AuthenticationPrincipal Alumno usuarioLog) {
        Alumno alum = alumnoServicio.findByEmail(usuarioLog.getEmail());
        model.addAttribute("usuarioLogeado", alum);
        model.addAttribute("asignaturas", asignaturaServicio.findActivasSegundo(alum));
        model.addAttribute("horariosAlumno", horarioServicio.ordenarFinal(horarioServicio.horariosPorAlumno(alum, ampliacionServicio.findAll())));
        model.addAttribute("horariosSegundo", horarioServicio.ordenarFinal(horarioServicio.encontrarPorAsignaturasAltaDeCurso(cursoServicio.cursoSegundoDeAlumno(alum))));
        model.addAttribute("ampliacionForm", new Ampliacion());
        model.addAttribute("primero", alum.getCurso());
        model.addAttribute("segundo", cursoServicio.cursoSegundoDeAlumno(alum));
        return "alumno/ampliacion";
    }

    @PostMapping("/alumno/ampliacion/submit")
    public String ampliacionForm(@ModelAttribute("ampliacionForm") Ampliacion ampliacion, @AuthenticationPrincipal Alumno usuarioLog) {

        AmpliacionPK pk = new AmpliacionPK(usuarioLog.getId(), ampliacion.getAsignatura().getId());
        ampliacion.setAlumno(usuarioLog);
        ampliacion.setId(pk);
        ampliacion.setEstado("Pendiente");
        ampliacion.setFechaSolicitud(LocalDate.now());
        if(ampliacionServicio.comprobarAmpliacion(usuarioLog, ampliacion.getAsignatura(), ampliacionServicio.findAll())){
            ampliacionServicio.save(ampliacion);
        }
        return "redirect:/alumno/principal";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().body(file);
    }


}
