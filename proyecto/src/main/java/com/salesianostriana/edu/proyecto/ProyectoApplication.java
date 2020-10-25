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

				System.out.println("");
//			excepcionServicio.nuevaExcepcion("alumno2@email.com", "1º DAM", "FOP");
//			ampliacionServicio.nuevaAmpliacion("alumno2@email.com", "2º DAM", "Sistemas de Gestión Empresarial");

				ExcepcionPK pk = new ExcepcionPK(15L, 3L);
				Excepcion ex = new Excepcion(pk, alumnoServicio.findById(15L), asignaturaServicio.findById(3L), LocalDate.now(), "Conv", "Pendiente");
				excepcionServicio.save(ex);
				ExcepcionPK pk2 = new ExcepcionPK(16L, 6L);
				Excepcion ex2 = new Excepcion(pk2, alumnoServicio.findById(16L), asignaturaServicio.findById(6L), LocalDate.now(), "Excep", "Pendiente");
				excepcionServicio.save(ex2);
				AmpliacionPK pk3 = new AmpliacionPK(10L,10L);
				AmpliacionPK pk4 = new AmpliacionPK(12L,11L);
				Ampliacion am1 = new Ampliacion(pk3, alumnoServicio.findById(10L), asignaturaServicio.findById(10L), LocalDate.now(), "Pendiente");
				Ampliacion am2 = new Ampliacion(pk4, alumnoServicio.findById(12L), asignaturaServicio.findById(11L), LocalDate.now(), "Pendiente");
				ampliacionServicio.save(am1);
				ampliacionServicio.save(am2);

			}
		};
	}



}
