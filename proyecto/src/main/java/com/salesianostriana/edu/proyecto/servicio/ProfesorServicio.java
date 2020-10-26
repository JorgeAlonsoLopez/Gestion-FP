package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.modelo.Usuario;
import com.salesianostriana.edu.proyecto.repositorio.ProfesorRepository;
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
public class ProfesorServicio extends BaseService<Profesor, Long, ProfesorRepository> {

    private final AsignaturaServicio asignaturaServicio;
    private final SendEmail sendEmail;
    private final UsuarioServicio usuarioServicio;


    public ProfesorServicio(ProfesorRepository repo, AsignaturaServicio asignaturaServicio, SendEmail sendEmail, UsuarioServicio usuarioServicio) {
        super(repo);
        this.asignaturaServicio = asignaturaServicio;
        this.sendEmail = sendEmail;
        this.usuarioServicio = usuarioServicio;
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

    public List<Profesor> findProfesor (){
        List<Profesor> lista = new ArrayList<>();
        for(Profesor p : this.findAll()){
            if(!p.isEsJefeDeEstudios()){
                lista.add(p);
            }
        }
        return lista;
    }

    public List<Profesor> findJefes(){
        List<Profesor> lista = new ArrayList<>();
        for(Profesor p : this.findAll()){
            if(p.isEsJefeDeEstudios()){
                lista.add(p);
            }
        }
        return lista;
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
                            values[0], values[1], true, true);
                } else {
                    return new Profesor(values[2], values[3], false,
                            values[0], values[1], true, false);
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

    public void cargarNuevoListadoJef(MultipartFile file) {
        int linea=0;
        BufferedReader br;
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is,  "UTF-8"));
            while ((line = br.readLine()) != null) {

                String [] values=line.split(";");
                if(!(linea==0)){
                    Profesor prof = new Profesor(values[2], true, values[3],
                            values[0], values[1], true, true);

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

    public void cargarNuevoListadoProf(MultipartFile file) {
        int linea=0;
        BufferedReader br;
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is,  "UTF-8"));
            while ((line = br.readLine()) != null) {

                String [] values=line.split(";");
                if(!(linea==0)){
                    Profesor prof = new Profesor(values[2], true, values[3],
                            values[0], values[1], true, false);

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
