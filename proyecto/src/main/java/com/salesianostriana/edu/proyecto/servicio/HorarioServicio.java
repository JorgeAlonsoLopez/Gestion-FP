package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.modelo.Curso;
import com.salesianostriana.edu.proyecto.modelo.Horario;
import com.salesianostriana.edu.proyecto.repositorio.HorarioRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HorarioServicio extends BaseService<Horario, Long, HorarioRepository> {

    private final AsignaturaServicio asignaturaServicio;
    private final CursoServicio cursoServicio;

    public HorarioServicio(HorarioRepository repo, AsignaturaServicio asignaturaServicio, CursoServicio cursoServicio) {
        super(repo);
        this.asignaturaServicio = asignaturaServicio;
        this.cursoServicio = cursoServicio;
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

    public boolean solapaHora(Horario horario){
        boolean encontrado = false;
        boolean mod = false;
        for(Horario h : this.findActivasByCurso(horario.getAsignatura().getCurso())){
            if( h.getDia() == horario.getDia() && h.getTramo() == horario.getTramo()){
               if(!mod){
                   mod=true;
                   encontrado = true;
               }
            }
        }

        return encontrado;
    }

    public List<Horario> findActivasByCurso(Curso curso){
        String nombre = curso.getNombre();
        List<Horario> lista = new ArrayList<>();

        for(Asignatura asig : asignaturaServicio.findByCurs(nombre)){
            if(asig.isEsAlta()){
                for(Horario h : asig.getHorarios()){
                    if(h.isEsAlta()) {
                        lista.add(h);
                    }
                }
            }

        }
        return lista;
    }

    public List<Horario> findByCursoActivo(){
        List<Horario> lista = new ArrayList<>();
        for(Curso curso : cursoServicio.findAll()) {
            if (curso.isEsAlta()) {
                for (Asignatura asig : asignaturaServicio.findByCurs(curso.getNombre())) {
                    if (asig.isEsAlta()) {
                        for (Horario h : asig.getHorarios()) {
                            lista.add(h);
                        }
                    }
                }
            }
        }
        return lista;
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
