package com.salesianostriana.edu.proyecto;

import com.salesianostriana.edu.proyecto.modelo.*;
import com.salesianostriana.edu.proyecto.servicio.*;
import com.salesianostriana.edu.proyecto.upload.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

	@Bean
	public CommandLineRunner app(TituloServicio tituloServicio, CursoServicio cursoServicio, AsignaturaServicio asignaturaServicio,
			 HorarioServicio horarioServicio, ProfesorServicio profesorServicio, AlumnoServicio alumnoServicio,
			 BCryptPasswordEncoder passwordEncoder, ExcepcionServicio excepcionServicio, AmpliacionServicio ampliacionServicio,
			 StorageService storageService){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

			storageService.deleteAll();
			storageService.init();
			tituloServicio.cargarListado();
			cursoServicio.cargarListado();
			asignaturaServicio.cargarListado();
			profesorServicio.cargarListado(passwordEncoder);
			alumnoServicio.cargarListado(passwordEncoder);
			horarioServicio.cargarListado();

			}
		};
	}



}
