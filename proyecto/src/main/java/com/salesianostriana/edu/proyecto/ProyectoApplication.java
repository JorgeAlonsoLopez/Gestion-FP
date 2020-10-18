package com.salesianostriana.edu.proyecto;

import com.salesianostriana.edu.proyecto.modelo.Alumno;
import com.salesianostriana.edu.proyecto.modelo.Asignatura;
import com.salesianostriana.edu.proyecto.modelo.Curso;
import com.salesianostriana.edu.proyecto.modelo.Profesor;
import com.salesianostriana.edu.proyecto.servicio.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

	@Bean
	public CommandLineRunner app(TituloServicio tituloServicio, CursoServicio cursoServicio, AsignaturaServicio asignaturaServicio,
			 HorarioServicio horarioServicio, ProfesorServicio profesorServicio, AlumnoServicio alumnoServicio,
			 BCryptPasswordEncoder passwordEncoder, ExcepcionServicio excepcionServicio, AmpliacionServicio ampliacionServicio){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

			tituloServicio.cargarListado();
			cursoServicio.cargarListado();
			asignaturaServicio.cargarListado();
			profesorServicio.cargarListado(passwordEncoder);
			profesorServicio.cargarListadoProf_Asig();
			alumnoServicio.cargarListado(passwordEncoder);
			alumnoServicio.cargarListadoAsignaturas();
			horarioServicio.cargarListado();

			alumnoServicio.deleteAsignatura("alumno2@email.com", "Bases de Datos", "1º DAM");
			alumnoServicio.deleteAsignatura("alumno2@email.com", "Lenguajes de Marcas y Sistemas de Gestión de la Información", "1º DAM");

//			excepcionServicio.nuevaExcepcion("alumno2@email.com", "1º DAM", "FOP");
//			ampliacionServicio.nuevaAmpliacion("alumno2@email.com", "2º DAM", "Sistemas de Gestión Empresarial");

			}
		};
	}



}
