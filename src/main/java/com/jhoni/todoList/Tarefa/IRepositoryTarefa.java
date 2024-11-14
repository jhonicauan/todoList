package com.jhoni.todoList.Tarefa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface IRepositoryTarefa extends JpaRepository<ModelTarefa,UUID>{
    List<ModelTarefa> findByIdUsuario(UUID idUsuario);
    ModelTarefa findByIdAndIdUsuario(UUID id, UUID idUsuario);
}
