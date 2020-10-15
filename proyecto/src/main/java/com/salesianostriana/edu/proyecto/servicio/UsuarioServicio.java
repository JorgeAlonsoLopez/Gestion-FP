package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Usuario;
import com.salesianostriana.edu.proyecto.repositorio.UsuarioRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicio extends BaseService<Usuario, Long, UsuarioRepository> {
    public UsuarioServicio(UsuarioRepository repo) {
        super(repo);
    }
    public Optional<Usuario> buscarPorUsuario(String correo) {
        return repositorio.findFirstByCorreo(correo);
    }
}
