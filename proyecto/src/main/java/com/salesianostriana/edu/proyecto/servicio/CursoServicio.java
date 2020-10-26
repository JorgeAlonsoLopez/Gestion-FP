package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Curso;
import com.salesianostriana.edu.proyecto.modelo.Titulo;
import com.salesianostriana.edu.proyecto.repositorio.CursoRepositorio;
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
public class CursoServicio extends BaseService<Curso, Long, CursoRepositorio> {

    private final TituloServicio tituloServicio;

    public CursoServicio(CursoRepositorio repo, TituloServicio tituloServicio) {
        super(repo);
        this.tituloServicio = tituloServicio;
    }

    public Curso findByName(String nombre){

        Curso curso = new Curso();
        curso=null;

        for (Curso t : this.findAll()){
            if(t.getNombre().equals(nombre)){
                curso = t;
            }
        }
        return curso;
    }

    public List<Curso> listaActivos(){

        List<Curso> lista = new ArrayList<>();
        for(Titulo t : tituloServicio.findAll()){
            if(t.isEsAlta()){
                for( Curso c : t.getListaCursos()){
                    if(c.isEsAlta()){
                        lista.add(c);
                    }
                }
            }
        }
        return lista;
    }

    public Curso cursoSegundoDeAlumno(Alumno alumno){

        Curso curso = new Curso();

        for(Curso c : alumno.getCurso().getTitulo().getListaCursos()){
            if(c.getAnyo()==2){
                curso = c;
            }
        }

        return curso;
    }

    public List<Alumno> listaAlumnosActivos(){

        List<Alumno> lista = new ArrayList<>();
        for(Titulo t : tituloServicio.findAll()){
            if(t.isEsAlta()){
                for( Curso c : t.getListaCursos()){
                    if(c.isEsAlta()){
                        for(Alumno a : c.getAlumnos()){
                            if(a.isEsAlta()){
                                lista.add(a);
                            }
                        }
                    }
                }
            }
        }
        return lista;
    }

    public List<Curso> listaDisponibles(){

        List<Curso> lista = new ArrayList<>();
        for(Titulo t : tituloServicio.findAll()){
            if(t.isEsAlta()){
                for( Curso c : t.getListaCursos()){
                    lista.add(c);

                }
            }
        }
        return lista;
    }

    public void cargarListado() {
        List<Curso> result = new ArrayList<>();

        String path = "classpath:Cursos.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");
                return new Curso(values[1], Integer.parseInt(values[2]), tituloServicio.findByName(values[0]), true);

            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de cursos.");
            System.exit(-1);
        }

        // MIRAR SE DUPILCA
        for(Curso c : result){
            c.getTitulo().addCurso(c);
            this.save(c);
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
                    Curso prof = new Curso(values[1], Integer.parseInt(values[2]), tituloServicio.findByName(values[0]), true);

                    boolean encontrado=false;
                    for(Curso g : this.findAll()){
                        if((prof.getNombre().equals(g.getNombre())) && (prof.getTitulo().getNombre().equals(g.getTitulo().getNombre()))){
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
