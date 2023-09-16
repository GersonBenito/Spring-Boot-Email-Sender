package com.spring.mail.sender.controllers;

import com.spring.mail.sender.controllers.dto.EmailDTO;
import com.spring.mail.sender.controllers.dto.EmailFileDTO;
import com.spring.mail.sender.services.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MailController {

    @Autowired
    private IEmailService emailService;

    @PostMapping("/sendMessage")
    public ResponseEntity<Map<String, String>> receiveRequestEmail(@RequestBody EmailDTO emailDTO){
        // Json --> Java Object = Deserialization
        // Java Object --> Json = Serialization

        // libreria que hace esto se llama Jackson serializable

        log.info("Enviando mensaje " + emailDTO);

        Map<String, String> response = new HashMap<>();

        emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());

        response.put("message", "Correo enviado");
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendMessageFile")
    public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO){
        // @ModelAttribute se usa cuando se trabajao con archivos, se accede al form data
        Map<String, String> response = new HashMap<>();

        try {
            String fileName  = emailFileDTO.getFile().getOriginalFilename(); // obtener el nombre del archivo
            Path path = Paths.get("src/main/resources/file/" + fileName); // obtener la ruta

            Files.createDirectories(path.getParent()); // crear el directorio por si no existe

            // copiar o reemplazar el archivo en el directorio
            Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile(); // obtener la ferencia del archivo

            emailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), emailFileDTO.getMessage(), file);

            response.put("estado", "Enviado");
            response.put("archivo", fileName);
            response.put("message", "Correo enviado");

            return ResponseEntity.ok(response);

        }catch (Exception e){
            response.put("estado", "Failed");
            response.put("message", "Error al enviar");
            log.error("Error al enviar correo ".concat(e.getMessage()));
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
