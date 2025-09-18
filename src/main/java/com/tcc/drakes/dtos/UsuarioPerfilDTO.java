package com.tcc.drakes.dtos;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.tcc.drakes.entities.Usuario;

public class UsuarioPerfilDTO {

	private String nome;
	private String biografia;
	private int pontuacao;


	private boolean online;

	public UsuarioPerfilDTO(Usuario usuario) {
		this.nome = usuario.getNome();
		this.biografia = usuario.getBiografia();
		this.pontuacao = usuario.getPontuacao();

		
		this.online = false; 


		if (usuario.getDataUltimaAtividade() != null) {
			long minutosDesdeUltimaAtividade = ChronoUnit.MINUTES.between(usuario.getDataUltimaAtividade(), LocalDateTime.now());

			if (minutosDesdeUltimaAtividade <= 5) {
				this.online = true;
			}
		}
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
	
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
}