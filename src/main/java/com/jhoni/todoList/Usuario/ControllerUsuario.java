package com.jhoni.todoList.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class ControllerUsuario {
    
    @Autowired
    IRepositoryUsuario repositoryUsuario;

    @GetMapping("/test")
    public String test(){
        return "Ola mundo";
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody ModelUsuario modelUsuario){
        var verificarUsuario = repositoryUsuario.findByUsuario(modelUsuario.getUsuario());
        if(verificarUsuario != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já em uso");
        }

        var senha = BCrypt.withDefaults().hashToString(12, modelUsuario.getSenha().toCharArray());

        modelUsuario.setSenha(senha);

        var usuario = repositoryUsuario.save(modelUsuario);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);
    }
}
