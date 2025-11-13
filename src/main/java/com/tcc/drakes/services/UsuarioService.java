package com.tcc.drakes.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <<< ADICIONADO IMPORT

import com.tcc.drakes.dtos.LoginDTO;
import com.tcc.drakes.dtos.UsuarioDTO;
import com.tcc.drakes.dtos.UsuarioListaDTO;
import com.tcc.drakes.dtos.UsuarioPerfilDTO;
import com.tcc.drakes.entities.Role;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.RoleRepository;
import com.tcc.drakes.repositories.UsuarioRepository;
import com.tcc.drakes.security.JwtUtil;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
	}

	public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
		if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
			throw new RuntimeException("Email já cadastrado.");
		}

		Usuario usuario = new Usuario();
		usuario.setNome(usuarioDTO.getNome());
		usuario.setAvatar(usuarioDTO.getAvatar());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setBiografia(usuarioDTO.getBiografia());
		usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

		Role userRole = roleRepository.findByNome("ROLE_USER")
				.orElseThrow(() -> new RuntimeException("Erro: Role padrão USER não encontrada."));

		usuario.setRoles(new HashSet<>(Arrays.asList(userRole)));

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

	public Usuario atualizarAvatar(Long id, String novoAvatar) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			usuario.setAvatar(novoAvatar);
			return usuarioRepository.save(usuario);
		} else {
			throw new RuntimeException("Usuário não encontrado com o ID: " + id);
		}
	}

	public Usuario atualizarRoles(Long usuarioId, List<Long> roleIds) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

		Set<Role> novasRoles = new HashSet<>(roleRepository.findAllById(roleIds));

		usuario.setRoles(novasRoles);

		return usuarioRepository.save(usuario);
	}

	public List<UsuarioListaDTO> listarUsuariosSimplificado() {
		return usuarioRepository.findAll().stream()
				.map(usuario -> new UsuarioListaDTO(usuario.getId(), usuario.getNome(), usuario.getPontuacao()))
				.collect(Collectors.toList());
	}

	public UsuarioPerfilDTO buscarPerfilPorId(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

		return new UsuarioPerfilDTO(usuario);
	}

	// =========================================================================
	// ✅ NOVO MÉTODO ADICIONADO (PASSO 1)
	// =========================================================================
	/**
	 * Busca TODOS os usuários e os converte para UsuarioPerfilDTO.
	 * O UsuarioPerfilDTO já contém a lógica para calcular o status 'online'.
	 */
	@Transactional(readOnly = true) // Boa prática para métodos de leitura
	public List<UsuarioPerfilDTO> listarPerfisDeUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		// Itera e usa o construtor do DTO que já calcula o status online
		return usuarios.stream()
				.map(usuario -> new UsuarioPerfilDTO(usuario)) 
				.collect(Collectors.toList());
	}
}