package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Usuario;
import com.salesianostriana.edu.proyecto.repositorio.UsuarioRepository;
import com.salesianostriana.edu.proyecto.servicio.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioServicio extends BaseService<Usuario, Long, UsuarioRepository> {
    public UsuarioServicio(UsuarioRepository repo) {
        super(repo);
    }
    public Optional<Usuario> buscarPorUsuario(String email) {
        return repositorio.findFirstByEmail(email);
    }

    private String generarCódigo(){
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

    public void generarCodigo(String email){
        Usuario u = this.buscarPorUsuario(email).get();
        u.setCodigoBienv(this.generarCódigo());
        this.edit(u);
    }


}
