package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Ranking;

public class RankingDTO {

	private Long idRanking;
	
	private Long idUsuario;
	
	private Long idSala;
	
	private Long pontuacao;
	
	private String ultimaAtualização;

	public RankingDTO() {
	}

	public RankingDTO(Long idRanking, Long idUsuario, Long idSala, Long pontuacao, String ultimaAtualização) {
		this.idRanking = idRanking;
		this.idUsuario = idUsuario;
		this.idSala = idSala;
		this.pontuacao = pontuacao;
		this.ultimaAtualização = ultimaAtualização;
	}

	
	public RankingDTO(Ranking entity) {
		idRanking = entity.getIdRanking();
		idUsuario = entity.getIdUsuario();
		idSala = entity.getIdSala();
		pontuacao = entity.getPontuacao();
		ultimaAtualização = entity.getUltimaAtualização();
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
