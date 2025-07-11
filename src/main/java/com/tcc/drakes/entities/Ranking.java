package com.tcc.drakes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "tb_ranking")
public class Ranking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRanking;
	
	private Long idUsuario;
	
	private Long idSala;
	
	private Long pontuacao;
	
	private String ultimaAtualização;

	public Ranking() {
	}

	public Ranking(Long idRanking, Long idUsuario, Long idSala, Long pontuacao, String ultimaAtualização) {
		this.idRanking = idRanking;
		this.idUsuario = idUsuario;
		this.idSala = idSala;
		this.pontuacao = pontuacao;
		this.ultimaAtualização = ultimaAtualização;
	}

	public Long getIdRanking() {
		return idRanking;
	}

	public void setIdRanking(Long idRanking) {
		this.idRanking = idRanking;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdSala() {
		return idSala;
	}

	public void setIdSala(Long idSala) {
		this.idSala = idSala;
	}

	public Long getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Long pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getUltimaAtualização() {
		return ultimaAtualização;
	}

	public void setUltimaAtualização(String ultimaAtualização) {
		this.ultimaAtualização = ultimaAtualização;
	}
	
	
	
	
}
