package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Titulo;
import com.salesianostriana.edu.proyecto.repositorio.TituloRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TituloServicio extends BaseService<Titulo, Long, TituloRepository> {

    public TituloServicio(TituloRepository repo) {
        super(repo);
    }

    public Titulo findByName(String nombre){

        Titulo titulo = new Titulo();
        titulo=null;

        for (Titulo t : this.findAll()){
            if(t.getNombre().equals(nombre)){
                titulo = t;
            }
        }
        return titulo;
    }

    public void cargarListado() {
        List<Titulo> result = new ArrayList<>();

        String path = "classpath:Titulos.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");
                return new Titulo(values[0], true);

            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de títulos.");
            System.exit(-1);
        }

        for(Titulo t : result){
            this.save(t);
        }

    }




}
