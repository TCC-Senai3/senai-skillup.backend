package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.TipoUsuario;

public class UsuarioDTO {
	private String nome;
	private String email;
	private String senha;
	//private TipoUsuario tipoUsuario;

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

//	public TipoUsuario getTipoUsuario() {
	//	return tipoUsuario;
	//}

	//public void setTipoUsuario(TipoUsuario tipoUsuario) {
		//this.tipoUsuario = tipoUsuario;
	//}

}
