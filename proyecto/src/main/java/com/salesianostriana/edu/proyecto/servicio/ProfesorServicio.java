package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.repositorio.ProfesorRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProfesorServicio extends BaseService<Profesor, Long, ProfesorRepository> {

    private final AsignaturaServicio asignaturaServicio;

    public ProfesorServicio(ProfesorRepository repo, AsignaturaServicio asignaturaServicio) {
        super(repo);
        this.asignaturaServicio = asignaturaServicio;
    }

    public Profesor findByEmail(String email){

        Profesor prof = new Profesor();
        prof=null;

        for (Profesor t : this.findAll()){
            if(t.getEmail().equals(email)){
                prof = t;
            }
        }
        return prof;
    }

    public void cargarListado(BCryptPasswordEncoder passwordEncoder) {
        List<Profesor> result = new ArrayList<>();

        String path = "classpath:Profesores.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");
                if (values[4].equals("True")) {
                    return new Profesor(values[2], values[3], false,
                            values[0], values[1], true);
                } else {
                    return new Profesor(values[2], values[3], false,
                            values[0], values[1], false);
                }


            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de profesores.");
            System.exit(-1);
        }

        for(Profesor p : result){
            p.setContrasenya(passwordEncoder.encode(p.getContrasenya()));
            this.save(p);
        }

    }

    public String codigo(){
        Random aleatorio = new Random();

        String alfa = "ABCDEFGHIJKLMNOPQRSTVWXYZ";

        String cadena = "";

        int numero;

        int forma;
        forma=(int)(aleatorio.nextDouble() * alfa.length()-1+0);
        numero=(int)(aleatorio.nextDouble() * 99+100);

        cadena=cadena+alfa.charAt(forma)+numero;

        return cadena;
    }



}
