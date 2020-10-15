package com.salesianostriana.edu.proyecto.seguridad;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final UserDetailsService userDetailsService;
	private final CustomSuccessHandler customSuccessHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		
		http
		.authorizeRequests()
		.antMatchers("/css/**","/js/**","/img/**", "/h2-console/**", "/", "/acceso", "/index", "/nuevo", "/nuevo/submit").permitAll()
			.antMatchers("/jefe/**").hasAnyRole("JEFE")
			.antMatchers("/profesor/**").hasAnyRole("PROF")
			.antMatchers("/alumno/**").hasAnyRole("ALUM")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.successHandler(customSuccessHandler)
			.and()
		.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/index")
			.permitAll()
			.and()
		.exceptionHandling()
			.accessDeniedPage("/acceso");
	
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		// @formatter:on

	}
	
}
