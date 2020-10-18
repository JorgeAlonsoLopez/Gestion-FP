package com.salesianostriana.edu.proyecto.seguridad;


import com.salesianostriana.edu.proyecto.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UsuarioServicio usuarioServicio;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioServicio.buscarPorUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
	}

}
