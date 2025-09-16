package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Usuario;

public class UsuarioPerfilDTO {

	private String nome;
	private String biografia;
	private int pontuacao;

	public UsuarioPerfilDTO(Usuario usuario) {
		this.nome = usuario.getNome();
		this.biografia = usuario.getBiografia();
		this.pontuacao = usuario.getPontuacao();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
}