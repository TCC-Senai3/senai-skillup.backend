package com.tcc.drakes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder; // Use a interface PasswordEncoder
import com.tcc.drakes.entities.enums.TipoUsuario; // Importe nosso Enum de permissão

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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
		if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
			throw new RuntimeException("Email já cadastrado.");
		}

		Usuario usuario = new Usuario();
		usuario.setNome(usuarioDTO.getNome());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setBiografia(usuarioDTO.getBiografia());

		usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

		usuario.setTipoUsuario(TipoUsuario.USER);

		return usuarioRepository.save(usuario);
	}

	public String loginComJwt(LoginDTO loginDTO) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(loginDTO.getEmail());

		if (usuario.isPresent() && passwordEncoder.matches(loginDTO.getSenha(), usuario.get().getSenha())) {
			return jwtUtil.generateToken(usuario.get().getEmail());
		}
		throw new RuntimeException("Credenciais inválidas");
	}

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