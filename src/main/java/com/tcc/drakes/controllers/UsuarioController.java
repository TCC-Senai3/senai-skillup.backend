package com.tcc.drakes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.dtos.AuthResponseDTO;
import com.tcc.drakes.dtos.LoginDTO;
import com.tcc.drakes.dtos.UsuarioDTO;
import com.tcc.drakes.dtos.UsuarioListaDTO;
import com.tcc.drakes.dtos.UsuarioPerfilDTO;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public Usuario cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.criarUsuario(usuarioDTO);
    }

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
    
    @GetMapping
    public ResponseEntity<List<UsuarioPerfilDTO>> getListaDeUsuarios() {
        List<UsuarioPerfilDTO> listaDePerfis = usuarioService.listarPerfisDeUsuarios(); 
        return ResponseEntity.ok(listaDePerfis);
    }
    // =========================================================================

    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPerfilDTO> getUsuarioParaPerfil(@PathVariable Long id) {
        try {
            UsuarioPerfilDTO usuarioDTO = usuarioService.buscarPerfilPorId(id);
            return ResponseEntity.ok(usuarioDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Retorna o perfil do usuário atualmente autenticado (logado).
     */
    @GetMapping("/me")
    public ResponseEntity<UsuarioPerfilDTO> getMeuPerfil() {
        try {
            // Pega o usuário autenticado a partir do token JWT
            Usuario usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            
            // Reutiliza o seu método de serviço que busca por ID
            UsuarioPerfilDTO usuarioDTO = usuarioService.buscarPerfilPorId(usuarioAutenticado.getId());
            
            return ResponseEntity.ok(usuarioDTO);
        } catch (Exception e) {
            // Se o token for inválido ou ocorrer outro erro, retorna não autorizado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/{id}/avatar")
    public ResponseEntity<Usuario> atualizarAvatar(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarAvatar(id, usuarioDTO.getAvatar());
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

