package com.jhoni.todoList.Tarefa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhoni.todoList.Utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class ControllerTarefa {
    
    @Autowired
    IRepositoryTarefa repositoryTarefa;

    @GetMapping("/test")
    public String test(){
        return "Bom dia";
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody ModelTarefa modelTarefa,HttpServletRequest request){
        UUID idUsuario = (UUID) request.getAttribute("idUsuario");

        modelTarefa.setIdUsuario(idUsuario);

        LocalDateTime horaAtual;
        horaAtual = LocalDateTime.now();
        if(horaAtual.isAfter(modelTarefa.getDtInicio()) || horaAtual.isAfter(modelTarefa.getDtFim())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As horas de inicio ou fim devem ser definidas em uma data de hoje em diante");
        }
        if(modelTarefa.getDtInicio().isAfter(modelTarefa.getDtFim())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio não pode ser após a data final");
        }
        var tarefa = repositoryTarefa.save(modelTarefa);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tarefa);
    }

    @GetMapping("/list")
    public List<ModelTarefa> list(HttpServletRequest request){
        UUID idUsuario = (UUID) request.getAttribute("idUsuario");

        List<ModelTarefa> tarefas = repositoryTarefa.findByIdUsuario(idUsuario);
        return tarefas;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody ModelTarefa modelTarefa,HttpServletRequest request,@PathVariable UUID id){
        var idUsuario = (UUID) request.getAttribute("idUsuario");
        
        var tarefa = repositoryTarefa.findByIdAndIdUsuario(id, idUsuario);

        if(tarefa == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este usuario não pode editar a tarefa");
        }

        Utils.copiarPropriedades(modelTarefa,tarefa);
        repositoryTarefa.save(tarefa);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tarefa);
    }
}
