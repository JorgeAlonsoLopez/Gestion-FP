package com.salesianostriana.edu.proyecto.controller;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.servicio.*;
import com.salesianostriana.edu.proyecto.upload.storage.FileSystemStorageService;
import com.salesianostriana.edu.proyecto.upload.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class JefeController {

    private final ProfesorServicio profesorServicio;
    private final AlumnoServicio alumnoServicio;
    private final CursoServicio cursoServicio;
    private final TituloServicio tituloServicio;
    private final SendEmail sendEmail;
    private final AsignaturaServicio asignaturaServicio;
    private final HorarioServicio horarioServicio;
    private final ExcepcionServicio excepcionServicio;
    private final AmpliacionServicio ampliacionServicio;
    private final FileSystemStorageService fileSystemStorageService;



    @GetMapping("/jefe/principal")
    public String jefeEstudios(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));

        return "jefe/principal";
    }

    @GetMapping("/jefe/alumnos")
    public String alumnos(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaActivos());
        return "jefe/alumnos";
    }


    @GetMapping("/jefe/alumnos/{id}")
    public String alumnosAltaBaja(@PathVariable("id") Long id) {
        Alumno modificado = alumnoServicio.findById(id);
        if(modificado.isEsAlta()){
            modificado.setEsAlta(false);
        }else{
            modificado.setEsAlta(true);
        }
        alumnoServicio.edit(modificado);
        return "redirect:/jefe/alumnos";
    }

    @GetMapping ("/jefe/editarAlumno/{id}")
    public String editarAlumno ( Model model, @AuthenticationPrincipal  Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("alumno", alumnoServicio.findById(id));
        model.addAttribute("cursos", cursoServicio.listaActivos());
        return "jefe/editarAlumno";
    }

    @PostMapping("/jefe/editarAlumno/submit")
    public String editarAlumnoSubmit(@ModelAttribute("alumno") Alumno alumno) {
        alumno.setEsAlta(true);
        alumno.setCodigoBienv(null);
        alumnoServicio.edit(alumno);
        return "redirect:/jefe/alumnos";
    }

    @GetMapping("/jefe/profesores")
    public String profesores(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("profesores", profesorServicio.findProfesor());
        return "jefe/profesores";
    }


    @GetMapping("/jefe/profesores/{id}")
    public String profesoresAltaBaja(@PathVariable("id") Long id) {
        Profesor modificado = profesorServicio.findById(id);
        if(modificado.isEsAlta()){
            modificado.setEsAlta(false);
        }else{
            modificado.setEsAlta(true);
        }
        profesorServicio.edit(modificado);
        return "redirect:/jefe/profesores";
    }

    @GetMapping ("/jefe/editarProfesor/{id}")
    public String editarProfesor ( Model model, @AuthenticationPrincipal  Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("profesor", profesorServicio.findById(id));
        return "jefe/editarProfesor";
    }

    @PostMapping("/jefe/editarProfesor/submit")
    public String editarProfesorSubmit(@ModelAttribute("profesor") Profesor profesor) {
        profesor.setEsAlta(true);
        profesor.setEsJefeDeEstudios(false);
        profesor.setCodigoBienv(null);
        profesorServicio.edit(profesor);
        return "redirect:/jefe/profesores";
    }

    @GetMapping("/jefe/jefes")
    public String jefes(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("jefes", profesorServicio.findJefes());
        return "jefe/jefes";
    }


    @GetMapping("/jefe/jefes/{id}")
    public String jefesAltaBaja(@PathVariable("id") Long id) {
        Profesor modificado = profesorServicio.findById(id);
        if(modificado.isEsAlta()){
            modificado.setEsAlta(false);
        }else{
            modificado.setEsAlta(true);
        }
        profesorServicio.edit(modificado);
        return "redirect:/jefe/jefes";
    }

    @GetMapping ("/jefe/editarJefe/{id}")
    public String editarJefe ( Model model, @AuthenticationPrincipal  Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("jefe", profesorServicio.findById(id));
        return "jefe/editarJefe";
    }

    @PostMapping("/jefe/editarJefe/submit")
    public String editarJefeSubmit(@ModelAttribute("jefe") Profesor jefe) {
        jefe.setEsAlta(true);
        jefe.setEsJefeDeEstudios(true);
        jefe.setCodigoBienv(null);
        profesorServicio.edit(jefe);
        return "redirect:/jefe/jefes";
    }


    @GetMapping("/jefe/asignaturas")
    public String asignaturas(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos" , cursoServicio.listaActivos());
        return "jefe/asignaturas";
    }

    @GetMapping("/jefe/asignaturas/{id}")
    public String AsignaturaAltaBaja(@PathVariable("id") Long id) {
        Asignatura modificado = asignaturaServicio.findById(id);
        if(modificado.isEsAlta()){
            modificado.setEsAlta(false);
        }else{
            modificado.setEsAlta(true);
        }
        asignaturaServicio.edit(modificado);
        return "redirect:/jefe/asignaturas";
    }

    @GetMapping("/jefe/nuevoAsignatura")
    public String nuevoAsignatura(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("nuevaAsignatura", new Asignatura());
        model.addAttribute("cursos", cursoServicio.listaActivos());
        return "jefe/nuevoAsignatura";
    }

    @PostMapping("/jefe/nuevoAsignatura/submit")
    public String nuevoAsignaturaSubmit(@ModelAttribute("nuevaAsignatura") Asignatura nuevaAsignatura) {
        if(asignaturaServicio.findByNameCurs(nuevaAsignatura.getNombre(), nuevaAsignatura.getCurso().getNombre())==null){
            nuevaAsignatura.setEsAlta(true);
            asignaturaServicio.save(nuevaAsignatura);
        }
        return "redirect:/jefe/asignaturas";
    }

    @GetMapping ("/jefe/editarAsignatura/{id}")
    public String editarAsignatura ( Model model, @AuthenticationPrincipal  Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("asignatura", asignaturaServicio.findById(id));
        model.addAttribute("cursos", cursoServicio.listaActivos());
        return "jefe/editarAsignatura";
    }

    @PostMapping("/jefe/editarAsignatura/submit")
    public String editarAsignaturaSubmit(@ModelAttribute("asignatura") Asignatura asignatura) {
        asignatura.setEsAlta(true);
        asignaturaServicio.edit(asignatura);
        return "redirect:/jefe/asignaturas";
    }


    @GetMapping("/jefe/carnet/{id}")
    public String carnet(Model model,  @AuthenticationPrincipal Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("alumno", alumnoServicio.findById(id));
        model.addAttribute("horarios", horarioServicio.ordenarFinal(horarioServicio.horariosPorAlumno(alumnoServicio.findById(id), ampliacionServicio.findAll())));
        return "jefe/carnet";
    }


    @GetMapping("/jefe/cursos")
    public String cursos(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaDisponibles());
        return "jefe/cursos";
    }


    @GetMapping("/jefe/cursos/{id}")
    public String cursossAltaBaja(@PathVariable("id") Long id) {
        Curso modificado = cursoServicio.findById(id);
        if(modificado.isEsAlta()){
            modificado.setEsAlta(false);
        }else{
            modificado.setEsAlta(true);
        }
        cursoServicio.edit(modificado);
        return "redirect:/jefe/cursos";
    }

    @GetMapping("/jefe/nuevoCurso")
    public String nuevoCurso(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("nuevoCurso", new Curso());
        model.addAttribute("titulos", tituloServicio.listarActivos());
        return "jefe/nuevoCurso";
    }

    @PostMapping("/jefe/nuevoCurso/submit")
    public String nuevoCursoSubmit(@ModelAttribute("nuevoCurso") Curso nuevoCurso) {
        if(cursoServicio.findByName(nuevoCurso.getNombre())==null){
            nuevoCurso.setEsAlta(true);
            cursoServicio.save(nuevoCurso);
        }
        return "redirect:/jefe/cursos";
    }

    @GetMapping ("/jefe/editarCurso/{id}")
    public String editarCurso ( Model model, @AuthenticationPrincipal  Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("curso", cursoServicio.findById(id));
        model.addAttribute("titulos", tituloServicio.listarActivos());
        return "jefe/editarCurso";
    }

    @PostMapping("/jefe/editarCurso/submit")
    public String editarCursoSubmit(@ModelAttribute("curso") Curso curso) {
        curso.setEsAlta(true);

        cursoServicio.edit(curso);
        return "redirect:/jefe/cursos";
    }

    @GetMapping("/jefe/horarios")
    public String horarios(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaActivos());
        return "jefe/horarios";
    }

    @GetMapping("/jefe/horarios/{id}")
    public String horariosAltaBaja(@PathVariable("id") Long id) {
        Horario modificado = horarioServicio.findById(id);
        if(modificado.isEsAlta()){
            modificado.setEsAlta(false);
        }else{
            modificado.setEsAlta(true);
        }
        horarioServicio.edit(modificado);
        return "redirect:/jefe/horarios";
    }

    @GetMapping("/jefe/nuevoHorario")
    public String nuevoHorario(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("nuevoHorario", new Horario());
        model.addAttribute("asignaturas", asignaturaServicio.findActivas());
        return "jefe/nuevoHorario";
    }

    @PostMapping("/jefe/nuevoHorario/submit")
    public String nuevoCursoSubmit(@ModelAttribute("nuevoHorario") Horario nuevoHorario) {
        if(!horarioServicio.solapaHora(nuevoHorario)){
            nuevoHorario.setEsAlta(true);
            horarioServicio.save(nuevoHorario);
        }
        return "redirect:/jefe/horarios";
    }

    @GetMapping ("/jefe/editarHorario/{id}")
    public String editarHorario ( Model model, @AuthenticationPrincipal  Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("horario", horarioServicio.findById(id));
        model.addAttribute("asignaturas", asignaturaServicio.findActivas());
        return "jefe/editarHorario";
    }

    @PostMapping("/jefe/editarHorario/submit")
    public String editarHorarioSubmit(@ModelAttribute("horario") Horario horario) {
        if(!horarioServicio.solapaHora(horario)){
            horario.setEsAlta(true);
            horarioServicio.edit(horario);
        }
        return "redirect:/jefe/horarios";
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
            sendEmail.enviarCodigo(alumnoForm.getCodigoBienv());
            alumnoForm.setContrasenya(null);
            alumnoForm.setEsAlta(true);
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
            profForm.setContrasenya(null);
            sendEmail.enviarCodigo(profForm.getCodigoBienv());
            profForm.setContrasenya(null);
            profForm.setEsAlta(true);
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
            nuevoJefe.setContrasenya(null);
            sendEmail.enviarCodigo(nuevoJefe.getCodigoBienv());
            nuevoJefe.setEsAlta(true);
            profesorServicio.save(nuevoJefe);
        }
        return "redirect:/jefe/principal";
    }


    @GetMapping("/jefe/titulos")
    public String titulos(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("titulos", tituloServicio.findAll());
        return "jefe/titulos";
    }

    @GetMapping("/jefe/titulos/{id}")
    public String titulosAltaBaja(@PathVariable("id") Long id) {
        Titulo modificado = tituloServicio.findById(id);
        if(modificado.isEsAlta()){
            modificado.setEsAlta(false);
        }else{
            modificado.setEsAlta(true);
        }
        tituloServicio.edit(modificado);
        return "redirect:/jefe/titulos";
    }

    @GetMapping("/jefe/nuevoTitulo")
    public String nuevoTitulo(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("nuevoTitulo", new Titulo());
        return "jefe/nuevoTitulo";
    }

    @PostMapping("/jefe/nuevoTitulo/submit")
    public String nuevoTituloSubmit(@ModelAttribute("nuevoTitulo") Titulo nuevoTitulo) {
        if(tituloServicio.findByName(nuevoTitulo.getNombre())==null){
            nuevoTitulo.setEsAlta(true);
            tituloServicio.save(nuevoTitulo);
        }
        return "redirect:/jefe/titulos";
    }

    @GetMapping ("/jefe/editarTitulo/{id}")
    public String editarTitulo ( Model model, @AuthenticationPrincipal  Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("titulo", tituloServicio.findById(id));
        return "jefe/editarTitulo";
    }

    @PostMapping("/jefe/editarTitulo/submit")
    public String editarTituloSubmit(@ModelAttribute("titulo") Titulo titulo) {
        titulo.setEsAlta(true);
        tituloServicio.edit(titulo);
        return "redirect:/jefe/titulos";
    }

    @GetMapping("/jefe/clases")
    public String clases(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaActivos());
        return "jefe/clases";
    }

    @GetMapping("/jefe/clasesDetalles/{id}")
    public String clasesDetalles(Model model,  @AuthenticationPrincipal Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("listaAsignatura",  horarioServicio.ordenarListaDeAsignaturas(cursoServicio.findById(id)));
        model.addAttribute("listadoAlumnos",  horarioServicio.agregarAlListadoElTipo(cursoServicio.findById(id)));


        return "jefe/clasesDetalles";
    }

    @GetMapping ("/jefe/csv")
    public String csvForm( Model model, @AuthenticationPrincipal  Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("objeto", new Horario());
        return "jefe/csv";
    }

    @PostMapping("/jefe/csv/submit")
    public String cargarCsv(@ModelAttribute("objeto") Horario obj, @RequestParam("file") MultipartFile file) {

        if(!file.isEmpty()){
            String tipo = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 3);
            if(tipo.equals("csv")){
                switch (obj.getTramo()){
                    case 1:
                        tituloServicio.cargarNuevoListado(file);
                        break;
                    case 2:
                        cursoServicio.cargarNuevoListado(file);
                        break;
                    case 3:
                        asignaturaServicio.cargarNuevoListado(file);
                        break;
                    case 4:
                        horarioServicio.cargarNuevoListado(file);
                        break;
                    case 5:
                        profesorServicio.cargarNuevoListadoJef(file);
                        break;
                    case 6:
                        profesorServicio.cargarNuevoListadoProf(file);
                        break;
                    case 7:
                        alumnoServicio.cargarNuevoListado(file);
                        break;
                    default:
                        break;
                }
            }


        }


        return "redirect:/jefe/principal";
    }

    @GetMapping("/jefe/excepciones")
    public String excepciones(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("excepciones", excepcionServicio.findAll());
        return "jefe/excepciones";
    }

    @GetMapping("/jefe/excepciones/detalles/{AlumnId}/{AsignId}")
    public String excepcionesDetalles(Model model,  @AuthenticationPrincipal Profesor usuarioLog, @PathVariable("AlumnId") Long idAlum, @PathVariable("AsignId") Long idAsig) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("excepcion", excepcionServicio.buscarPorId(idAlum, idAsig));
        return "jefe/excepcionesDetalle";
    }

    @GetMapping("/jefe/excepciones/aceptar/{AlumnId}/{AsignId}")
    public String excepcionesAceptar(@PathVariable("AlumnId") Long idAlum, @PathVariable("AsignId") Long idAsig) {
        excepcionServicio.aceptarExcepcion(excepcionServicio.buscarPorId(idAlum, idAsig));
        return "redirect:/jefe/excepciones";
    }

    @GetMapping("/jefe/excepciones/rechazar/{AlumnId}/{AsignId}")
    public String excepcionesRechazar(@PathVariable("AlumnId") Long idAlum, @PathVariable("AsignId") Long idAsig) {
        excepcionServicio.declinarExcepcion(excepcionServicio.buscarPorId(idAlum, idAsig));
        return "redirect:/jefe/excepciones";
    }


    @GetMapping("/jefe/ampliacion")
    public String ampliacion(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("ampliaciones", ampliacionServicio.findAll());
        return "jefe/ampliacion";
    }

    @GetMapping("/jefe/ampliacion/detalles/{AlumnId}/{AsignId}")
    public String ampliacionesDetalles(Model model,  @AuthenticationPrincipal Profesor usuarioLog, @PathVariable("AlumnId") Long idAlum, @PathVariable("AsignId") Long idAsig) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("ampliacion", ampliacionServicio.buscarPorId(idAlum, idAsig));
        model.addAttribute("horariosAlumno", horarioServicio.ordenarFinal(horarioServicio.horariosPorAlumno(alumnoServicio.findById(idAlum), ampliacionServicio.findAll())));
        model.addAttribute("horariosSegundo", horarioServicio.ordenarFinal(horarioServicio.encontrarPorAsignaturasAltaDeCurso(cursoServicio.cursoSegundoDeAlumno(alumnoServicio.findById(idAlum)))));
        return "jefe/ampliacionDetalles";
    }

    @GetMapping("/jefe/ampliacion/aceptar/{AlumnId}/{AsignId}")
    public String ampliacionesAceptar(@PathVariable("AlumnId") Long idAlum, @PathVariable("AsignId") Long idAsig) {
        ampliacionServicio.aceptarAmpliacion(ampliacionServicio.buscarPorId(idAlum, idAsig));
        return "redirect:/jefe/ampliacion";
    }

    @GetMapping("/jefe/ampliacion/rechazar/{AlumnId}/{AsignId}")
    public String ampliacionesRechazar(@PathVariable("AlumnId") Long idAlum, @PathVariable("AsignId") Long idAsig) {
        ampliacionServicio.declinarAmpliacion(ampliacionServicio.buscarPorId(idAlum, idAsig));
        return "redirect:/jefe/ampliacion";
    }


    @GetMapping("/jefe/aprobados")
    public String aprobados(Model model,  @AuthenticationPrincipal Profesor usuarioLog) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("cursos", cursoServicio.listaActivos());
        return "jefe/aprobados";
    }

    @GetMapping("/jefe/aprobados/lista/{id}")
    public String aprobadosLista(Model model,  @AuthenticationPrincipal Profesor usuarioLog, @PathVariable("id") Long id) {
        model.addAttribute("usuarioLogeado", profesorServicio.findByEmail(usuarioLog.getEmail()));
        model.addAttribute("alumno", alumnoServicio.findById(id));
        model.addAttribute("asignaturas", asignaturaServicio.asignaturasPorAlumno(alumnoServicio.findById(id)));
        return "jefe/aprobadosAlumno";
    }

    @GetMapping("/jefe/aprobados/asignatura/{AlumnId}/{AsignId}")
    public String aprobadosListaAsignatura(Model model,  @AuthenticationPrincipal Profesor usuarioLog, @PathVariable("AsignId") Long AsignId,
                                           @PathVariable("AlumnId") Long AlumnId) {
        Asignatura asig = asignaturaServicio.findById(AsignId);
        Alumno alum = alumnoServicio.findById(AlumnId);

        alum.addAsignatura(asig);
        alumnoServicio.edit(alum);
        asignaturaServicio.edit(asig);

        return "redirect:/jefe/aprobados";
    }


    @GetMapping("/descargar/{nombre}")
    public ResponseEntity<Resource> descargar(@PathVariable("nombre") String nombre) {

        Resource resource = fileSystemStorageService.loadAsResource(nombre);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);


    }



}
