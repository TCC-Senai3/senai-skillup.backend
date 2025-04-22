package com.tcc.drakes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.dtos.LoginDTO;
import com.tcc.drakes.dtos.UsuarioDTO;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para registrar o usuário
    @PostMapping("/cadastro")
    public Usuario cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.criarUsuario(usuarioDTO);
    }

    // Endpoint para login
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return usuarioService.login(loginDTO)
                .map(usuario -> "Login realizado com sucesso!")
                .orElse("Credenciais inválidas");
    }
}