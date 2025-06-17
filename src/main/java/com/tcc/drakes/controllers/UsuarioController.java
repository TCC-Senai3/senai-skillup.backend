package com.tcc.drakes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.dtos.AuthResponseDTO;
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
    //@PostMapping("/login")
  //  public String login(@RequestBody LoginDTO loginDTO) {
       // return usuarioService.login(loginDTO)
        //        .map(usuario -> "Login realizado com sucesso!")
        //        .orElse("Credenciais inválidas");
   // }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            String token = usuarioService.loginComJwt(loginDTO);
            return ResponseEntity.ok().body(new AuthResponseDTO(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
    
    @PutMapping("/{id}/biografia")
    public ResponseEntity<Usuario> atualizarBiografia(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarBiografia(id, usuarioDTO.getBiografia());
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}