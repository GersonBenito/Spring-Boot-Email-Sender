package com.spring.mail.sender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailCofiguration {

    @Value("${email.sender}")
    private String emailUser;

    @Value("${email.password}")
    private String emailPassword;

    @Bean
    public JavaMailSenderImpl getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587); // por defecto el puerto de comunicacion es 587 para el smtp
        mailSender.setUsername(emailUser); // correo con el que se enviara
        mailSender.setPassword(emailPassword); // contraseña para autenticarse

        Properties prop = mailSender.getJavaMailProperties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true"); // habilitar la autenticacion
        prop.put("mail.smtp.starttls.enable", "true"); // habilitar el cifrado de los correos
        prop.put("mail.debug", "true"); // habilitar el debug, esto solo para desarrollo

        return mailSender;
    }
}
