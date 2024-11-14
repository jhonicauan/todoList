package com.jhoni.todoList.Filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jhoni.todoList.Usuario.IRepositoryUsuario;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.Base64;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTarefa extends OncePerRequestFilter{
    @Autowired
    IRepositoryUsuario repositoryUsuario;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var rota = request.getServletPath();
                if(rota.startsWith("/tasks")){
                    var autenticar = request.getHeader("authorization");
                    autenticar = autenticar.substring("Basic".length()).trim();

                    byte[] decodificar = Base64.getDecoder().decode(autenticar);

                    String dados = new String(decodificar);

                    String[] obterDados = dados.split(":");

                    String nome = obterDados[0];
                    String senha = obterDados[1];
                    
                    var verificarUsuario = repositoryUsuario.findByUsuario(nome);
                    if(verificarUsuario == null){
                        response.sendError(400);
                    }else{
                        var verificarSenha = BCrypt.verifyer().verify(senha.toCharArray(),verificarUsuario.getSenha().toCharArray());
                        if(!verificarSenha.verified){
                            response.sendError(400);
                        }else{
                            request.setAttribute("idUsuario", verificarUsuario.getId());
                            filterChain.doFilter(request, response);
                        }
                    }
        }else{
            filterChain.doFilter(request, response);
        }
    }
    
}
