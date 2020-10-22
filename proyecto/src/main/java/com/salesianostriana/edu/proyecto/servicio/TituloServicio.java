package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.modelo.Titulo;
import com.salesianostriana.edu.proyecto.repositorio.TituloRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
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


    public List<Titulo> listarActivos() {

        List<Titulo> lista = new ArrayList<>();

        for(Titulo t : this.findAll()){
            if(t.isEsAlta()){
                lista.add(t);
            }
        }

        return lista;
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
                    Titulo prof = new Titulo(values[0], true);

                    boolean encontrado=false;
                    for(Titulo g : this.findAll()){
                        if((prof.getNombre().equals(g.getNombre()))){
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
