package com.tcc.drakes.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tcc.drakes.entities.enums.TipoUsuario;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;
	private String email;
	private String senha;
	private String biografia;

	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	private int pontuacao = 0;

	public Usuario() {
	}

	public Usuario(long id, String nome, String email, String senha, String biografia, int pontuacao) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.biografia = biografia;

		this.pontuacao = pontuacao;
	}

	@OneToMany(mappedBy = "usuario")
	private List<Resposta> respostas;

	public long getId() {
		return id;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public void incrementarPontuacao() {
		this.pontuacao += 1;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Define as permissões (roles) do usuário.
		if (this.tipoUsuario == TipoUsuario.ADMIN) {
			// Um ADMIN tem permissão de ADMIN e de USER
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			// Um USER tem apenas a permissão de USER
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		// Vamos usar o e-mail como "username" para o login
		return this.email;
	}

	// Por enquanto, vamos deixar tudo como 'true'
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
