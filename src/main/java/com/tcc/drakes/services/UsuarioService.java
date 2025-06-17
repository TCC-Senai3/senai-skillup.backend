package com.tcc.drakes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcc.drakes.dtos.LoginDTO;
import com.tcc.drakes.dtos.UsuarioDTO;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.UsuarioRepository;
import com.tcc.drakes.security.JwtUtil;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Cadastro de novo usuário
    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setBiografia(usuarioDTO.getBiografia());
       // usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));// Criptografando a senha
      //  usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());
        
        return usuarioRepository.save(usuario);
    }
    
    @Autowired
    private JwtUtil jwtUtil;
// login com jwt
    public String loginComJwt(LoginDTO loginDTO) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(loginDTO.getEmail());
        if (usuario.isPresent() && passwordEncoder.matches(loginDTO.getSenha(), usuario.get().getSenha())) {
            return jwtUtil.generateToken(usuario.get().getEmail());
        }
        throw new RuntimeException("Credenciais inválidas");
    }

    // Login de usuário
  //  public Optional<Usuario> login(LoginDTO loginDTO) {
    //    Optional<Usuario> usuario = usuarioRepository.findByEmail(loginDTO.getEmail());
    //    if (usuario.isPresent() && passwordEncoder.matches(loginDTO.getSenha(), usuario.get().getSenha())) {
    //        return usuario; 
    //    }
   //     return Optional.empty(); // Credenciais inválidas
 //   }
    
    public Usuario atualizarBiografia(Long id, String novaBiografia) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setBiografia(novaBiografia);
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
    }
}
