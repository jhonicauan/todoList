package com.jhoni.todoList.Usuario;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface IRepositoryUsuario extends JpaRepository<ModelUsuario,UUID> {
    ModelUsuario findByUsuario(String usuario);
}
