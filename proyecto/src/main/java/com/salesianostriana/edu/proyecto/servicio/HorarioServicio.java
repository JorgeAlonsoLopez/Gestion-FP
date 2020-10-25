package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.repositorio.HorarioRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HorarioServicio extends BaseService<Horario, Long, HorarioRepository> {

    private final AsignaturaServicio asignaturaServicio;
    private final TituloServicio tituloServicio;
    private final ExcepcionServicio excepcionServicio;

    public HorarioServicio(HorarioRepository repo, AsignaturaServicio asignaturaServicio, TituloServicio tituloServicio, ExcepcionServicio excepcionServicio) {
        super(repo);
        this.asignaturaServicio = asignaturaServicio;
        this.tituloServicio = tituloServicio;
        this.excepcionServicio = excepcionServicio;
    }

    public void cargarListado() {
        List<Horario> result = new ArrayList<>();

        String path = "classpath:Horario.csv";
        try {
            // @formatter:off
            result = Files.lines(Paths.get(ResourceUtils.getFile(path).toURI())).skip(1).map(line -> {
                String[] values = line.split(";");
                return new Horario(Integer.parseInt(values[2]), Integer.parseInt(values[3]),
                        asignaturaServicio.findByNameCurs(values[0],values[1]), true);

            }).collect(Collectors.toList());
            // @formatter:on

        } catch (Exception e) {
            System.err.println("Error de lectura del fichero de datos de horarios.");
            System.exit(-1);
        }

        for(Horario h : result){
           // h.getAsigntura().addHorario(h);
            this.save(h);
           // asignaturaServicio.edit(h.getAsigntura());
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
                    Horario prof = new Horario(Integer.parseInt(values[2]), Integer.parseInt(values[3]),
                            asignaturaServicio.findByNameCurs(values[0],values[1]), true);

                    boolean encontrado=false;
                    for(Horario g : this.findAll()){
                        if((prof.getDia()==g.getDia()) && (prof.getTramo()==g.getTramo())
                                && (prof.getAsignatura().getNombre().equals(g.getAsignatura().getNombre()))
                                && (prof.getAsignatura().getCurso().equals(g.getAsignatura().getCurso()))){
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




    public boolean solapaHora(Horario horario){
        boolean encontrado = false;
        boolean mod = false;
        for(Horario h : this.encontrarPorAsignaturasAltaDeCurso(horario.getAsignatura().getCurso())){
            if( h.getDia() == horario.getDia() && h.getTramo() == horario.getTramo()){
               if(!mod){
                   mod=true;
                   encontrado = true;
               }
            }
        }

        return encontrado;
    }

    public List<Horario> encontrarPorAsignaturasAltaDeCurso(Curso curso){
        String nombre = curso.getNombre();
        List<Horario> lista = new ArrayList<>();
        Titulo t = tituloServicio.findByName(curso.getTitulo().getNombre());
        if(t.isEsAlta()){
            if(curso.isEsAlta()) {
                for (Asignatura asig : asignaturaServicio.findByCurs(nombre)) {
                    if (asig.isEsAlta()) {
                        for (Horario h : asig.getHorarios()) {
                            if (h.isEsAlta()) {
                                lista.add(h);
                            }
                        }
                    }
                }
            }
        }
        return lista;
    }

    public List<Horario> horariosPorAlumno(Alumno alumno, List<Ampliacion> listaAmpliaciones){

        List<Horario> listaHor = new ArrayList<>();
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



        for(Ampliacion ampl : listaAmpliaciones){
            if(ampl.getAlumno().equals(alumno)) {
                if(ampl.getEstado().equals("Aceptado")){
                    if(ampl.getAsignatura().isEsAlta()){
                        listaAsig.add(ampl.getAsignatura());
                    }
                }
            }
        }

        for(Asignatura asig : listaAsig){
            for(Horario hor : asig.getHorarios()){
                if(hor.isEsAlta()){
                    listaHor.add(hor);
                }
            }
        }

        return listaHor;
    }

    public List<List<Horario>> ordenarFinal (List<Horario> lista){

        List<List<Horario>> listaF = new ArrayList<>();
        for(int i=1;i<7;i++){
            listaF.add(this.ordenar(this.listaTramo(lista, i)));
        }

        return listaF;
    }



    public List<Horario> ordenar (List<Horario> lista){
        int plus=0;
        lista = lista.stream()
                .sorted(Comparator.comparingInt(Horario::getDia))
                .collect(Collectors.toList());
        //Buscamos si no están todas las horas ocupadas y contamos cuantas hay
        if(lista.size()<5) {
            plus = 5 - lista.size();
            for (int i = 0; i < plus; i++) {
                lista.add(new Horario(6));
            }

            boolean encontrado;
            for (int dia = 1; dia <= 5; dia++) {
                encontrado = false;
                //Buscamos para cada tramos, si para cada día está su hora
                    for (Horario h : lista) {
                        if (h.getDia() == dia) {
                            encontrado = true;
                        }
                    }
                //Si no encontramos la hora X del tramo que estamos tratando, tratamos una de relleno (las que tienen el dia=6)
                //y cambiamos su día por el que correspoda para rellenar el vacío dejado
                    if (!encontrado) {
                        for (int j = 0; j < lista.size(); j++) {
                            if (lista.get(j).getDia() == 6) {
                                lista.get(j).setDia(dia);
                                break;
                            }
                        }
                    }
            }

        }
        lista = lista.stream()
                .sorted(Comparator.comparingInt(Horario::getDia))
                .collect(Collectors.toList());

        return lista;
    }

    public List<Horario> listaTramo(List<Horario> lista, int dia){
        List<Horario> listaF = new ArrayList<>();
        for(Horario h : lista){
            if(h.getTramo()==dia){
                listaF.add(h);
            }
        }

        return listaF;
    }



}
