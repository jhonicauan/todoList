package com.jhoni.todoList.Tarefa;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tarefa")
public class ModelTarefa {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String titulo;

    private String descricao,prioridade;

    private LocalDateTime dtInicio,dtFim;

    private UUID idUsuario;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitulo(String titulo) throws Exception{
        if(titulo.length() > 50){
            throw new Exception("O campo não pode ter mais que 50 characters");
        }
        this.titulo = titulo;
    }
}
