package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class sendEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(Usuario usuario, String subject, String content){

        SimpleMailMessage email = new SimpleMailMessage();
        xml spring email;



    }

}
