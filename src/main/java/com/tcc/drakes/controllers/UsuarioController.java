package com.tcc.drakes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder; // <-- ADICIONE ESTA IMPORTAÇÃO
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
    
    /*
     * Endpoint de login alternativo para ambiente de desenvolvimento.
     * Este método não gera um token JWT, apenas verifica se as credenciais são válidas.
     * @param loginDTO Objeto com email e senha do usuário.
     * @return Uma string indicando se o login foi "Login válido" ou "Credenciais inválidas".
     
    @PostMapping("/login")
    public ResponseEntity<String> loginParaDesenvolvimento(@RequestBody LoginDTO loginDTO) {
        try {
            usuarioService.loginComJwt(loginDTO);
            return ResponseEntity.ok("Login válido");
        } catch (RuntimeException e) {
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
    */
    
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
	public ResponseEntity<List<UsuarioListaDTO>> getListaDeUsuarios() {
		List<UsuarioListaDTO> listaDeUsuarios = usuarioService.listarUsuariosSimplificado();
		return ResponseEntity.ok(listaDeUsuarios);
	}
    
    @GetMapping("/{id}")
	public ResponseEntity<UsuarioPerfilDTO> getUsuarioParaPerfil(@PathVariable Long id) {
		try {
			UsuarioPerfilDTO usuarioDTO = usuarioService.buscarPerfilPorId(id);
			return ResponseEntity.ok(usuarioDTO);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
    
    // NOVO ENDPOINT ADICIONADO ABAIXO
    /**
     * Retorna o perfil do usuário atualmente autenticado (logado).
     * O usuário é identificado através do token JWT enviado na requisição.
     * @return ResponseEntity com o UsuarioPerfilDTO do usuário logado.
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
}