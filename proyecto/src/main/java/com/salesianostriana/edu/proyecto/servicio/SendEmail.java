package com.salesianostriana.edu.proyecto.servicio;

import com.salesianostriana.edu.proyecto.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCodigo(String content){

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo("jorge75686@gmail.com");
        email.setSubject("Código de bienvenida");
        email.setText("El código de bienvenida es: "+content);

        mailSender.send(email);

    }

}
