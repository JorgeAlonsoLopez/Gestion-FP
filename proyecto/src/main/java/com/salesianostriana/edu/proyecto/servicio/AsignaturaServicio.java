package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.modelo.Curso;
import com.salesianostriana.edu.proyecto.repositorio.AsignaturaRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignaturaServicio extends BaseService<Asignatura, Long, AsignaturaRepository> {

    private final CursoServicio cursoServicio;
    private final ExcepcionServicio excepcionServicio;

    public AsignaturaServicio(AsignaturaRepository repo, CursoServicio cursoServicio, ExcepcionServicio excepcionServicio) {
        super(repo);
        this.cursoServicio = cursoServicio;
        this.excepcionServicio = excepcionServicio;
    }

    public Asignatura findByNameCurs(String nombre, String curso){

        Asignatura asign = new Asignatura();
        asign=null;

        for (Asignatura t : this.findAll()){
            if(t.getNombre().equals(nombre)){
                if(t.getCurso().getNombre().equals(curso)){
                    asign = t;
                }
            }
        }
        return asign;
    }

    public List<Asignatura> findActivas(){

        List<Asignatura> asign = new ArrayList<>();


        for (Curso a : cursoServicio.listaActivos()){
            for(Asignatura t : a.getAsignaturas()){
                if(t.isEsAlta()){
                    asign.add(t);
                }
            }

        }
        return asign;
    }

    public List<Asignatura> findActivasPorCurso(Curso curso){

        List<Asignatura> asign = new ArrayList<>();
        if(curso.isEsAlta()){
            for(Asignatura t : curso.getAsignaturas()){
                if(t.isEsAlta()){
                    asign.add(t);
                }
            }
        }
        return asign;
    }

    public List<Asignatura> findActivasSegundo(Alumno alumno){

        List<Asignatura> asign = new ArrayList<>();
        Curso curso = cursoServicio.cursoSegundoDeAlumno(alumno);
        if(curso.isEsAlta()){
            for(Asignatura t : curso.getAsignaturas()){
                if(t.isEsAlta()){
                    asign.add(t);
                }
            }
        }
        return asign;
    }

    public List<Asignatura> findByCurs(String curso){

        List<Asignatura> asign = new ArrayList<>();


        for (Asignatura t : this.findAll()){
            if(t.getCurso().getNombre().equals(curso)){
                asign.add(t);
            }
        }
        return asign;
    }

    public List<Asignatura> asignaturasPorAlumno(Alumno alumno){

        List<Asignatura> listaAsig = new ArrayList<>();


        for(Asignatura asig : alumno.getCurso().getAsignaturas()){
            if(asig.isEsAlta()){
                listaAsig.add(asig);
            }
        }

        for(int i = 0; i < listaAsig.size(); i++){
            if (excepcionServicio.buscarExistenciaTerminadaExcepcion(listaAsig.get(i), alumno).orElse(null)!=null) {
                listaAsig.remove(listaAsig.get(i));
            }
        }

        if(!alumno.getAsignaturas().isEmpty()){
            for(Asignatura asig : alumno.getAsignaturas()){
                for(int i = 0; i < listaAsig.size(); i++){
                    if(listaAsig.get(i).equals(asig)){
                        listaAsig.remove(asig);
                    }
                }
            }
        }

        return listaAsig;
    }

    public void cargarListado() {
        List<Asignatura> result = new ArrayList<>();

        String path = "classpath:Asignaturas.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");

                return new Asignatura(values[0], cursoServicio.findByName(values[1]), true);

            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de asignaturas.");
            System.exit(-1);
        }

        for(Asignatura a : result){
            a.getCurso().addAsignatura(a);
            this.save(a);
        }

    }

    public void cargarNuevoListado(MultipartFile file) {
        int linea=0;
        BufferedReader br;
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is,  "UTF-8"));
            while ((line = br.readLine()) != null) {

                String [] values=line.split(";");
                if(!(linea==0)){
                    Asignatura prof = new Asignatura(values[0], cursoServicio.findByName(values[1]), true);

                    boolean encontrado=false;
                    for(Asignatura g : this.findAll()){
                        if((prof.getNombre().equals(g.getNombre())) && (prof.getCurso().getNombre().equals(g.getCurso().getNombre()))){
                            encontrado=true;
                        }
                    }
                    if(!encontrado){
                        this.save(prof);
                    }
                }

                linea++;
            }

        } catch (InvalidParameterException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}




