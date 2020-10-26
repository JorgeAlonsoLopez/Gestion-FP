package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.repositorio.AlumnoRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AlumnoServicio extends BaseService<Alumno, Long, AlumnoRepository> {

    private final AsignaturaServicio asignaturaServicio;
    private final CursoServicio cursoServicio;
    private final SendEmail sendEmail;
    private final UsuarioServicio usuarioServicio;

    public AlumnoServicio(AlumnoRepository repo, AsignaturaServicio asignaturaServicio, CursoServicio cursoServicio, SendEmail sendEmail, UsuarioServicio usuarioServicio) {
        super(repo);
        this.asignaturaServicio = asignaturaServicio;
        this.cursoServicio = cursoServicio;
        this.sendEmail = sendEmail;
        this.usuarioServicio = usuarioServicio;
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

    public Alumno findByEmail(String email){

        Alumno alu = new Alumno();
        alu=null;

        for (Alumno a : this.findAll()){
            if(a.getEmail().equals(email)){
                alu = a;
            }
        }
        return alu;
    }



    public void cargarListado(BCryptPasswordEncoder passwordEncoder) {
        List<Alumno> result = new ArrayList<>();

        String path = "classpath:Alumnos.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");
                return new Alumno(values[2], values[3], false, values[0], values[1], true, cursoServicio.findByName(values[4]));


            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de alumnos.");
            System.exit(-1);
        }

        for (Alumno a : result) {
            a.setContrasenya(passwordEncoder.encode(a.getContrasenya()));
            a.getCurso().addAlumno(a);
            cursoServicio.edit(a.getCurso());
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
                    Alumno prof = new Alumno(values[2], true, values[3], values[0], values[1], true, cursoServicio.findByName(values[4]));

                    boolean encontrado=false;
                    for(Usuario g : usuarioServicio.findAll()){
                        if((prof.getEmail().equals(g.getEmail()))){
                            encontrado=true;
                        }
                    }
                    if(!encontrado){
                        prof.setContrasenya(null);
                        sendEmail.enviarCodigo(prof.getCodigoBienv());
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
