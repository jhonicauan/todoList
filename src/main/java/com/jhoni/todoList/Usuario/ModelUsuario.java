package com.jhoni.todoList.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_usuario")
public class ModelUsuario {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID Id;

    private String nome,senha;

    @Column(unique = true)
    private String usuario;

    @CreationTimestamp
    LocalDateTime createdAt;
}
