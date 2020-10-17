package com.salesianostriana.edu.proyecto;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.modelo.Curso;
import com.salesianostriana.edu.proyecto.servicio.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

	@Bean
	public CommandLineRunner app(TituloServicio tituloServicio, CursoServicio cursoServicio, AsignaturaServicio asignaturaServicio,
			 HorarioServicio horarioServicio, ProfesorServicio profesorServicio, AlumnoServicio alumnoServicio){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

			tituloServicio.cargarListado();
			cursoServicio.cargarListado(tituloServicio);
			asignaturaServicio.cargarListado(cursoServicio);
			profesorServicio.cargarListado(asignaturaServicio);
			profesorServicio.cargarListadoProf_Asig(asignaturaServicio);
			alumnoServicio.cargarListado(cursoServicio);
			alumnoServicio.cargarListadoAsignaturas(asignaturaServicio);
			horarioServicio.cargarListado(asignaturaServicio);

			}
		};
	}



}
