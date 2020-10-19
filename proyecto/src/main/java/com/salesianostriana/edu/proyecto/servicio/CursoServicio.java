package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Curso;
import com.salesianostriana.edu.proyecto.modelo.Titulo;
import com.salesianostriana.edu.proyecto.repositorio.CursoRepositorio;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
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



}
