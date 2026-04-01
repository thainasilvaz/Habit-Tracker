package com.thaina.habittracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thaina.habittracker.model.Usuario;
import com.thaina.habittracker.repository.UsuarioRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired //injeção automática de dependências  -> vai fornecer uma instância de usuarioRepository
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario loginRequest){ //metodo chamado quando alguem faz POST em /auth/login

        Usuario usuario = usuarioRepository //busca o usuário no banco
            .findByEmailAndSenha(loginRequest.getEmail(), loginRequest.getSenha()); 

        if(usuario == null){ //se nenhum usuário foi encontrado
            throw new RuntimeException("Email ou senha inválidos");
        }

        return usuario;
    }
    
}
