package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Resposta;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class RespostaDTO {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idResposta;
	
	private Long idUsuario;
	
	private Long idPergunta;
	
	private Long idSala;
	
	private String alternativaSelecionada;
	
	private long tempoGasto;
	
	public RespostaDTO(Resposta entity) {
		idResposta = entity.getIdResposta();
		idUsuario = entity.getIdUsuario();
		idPergunta = entity.getIdPergunta();
		idSala = entity.getIdSala();
		alternativaSelecionada = entity.getAlternativaSelecionada();
		tempoGasto = entity.getTempoGasto();
	}

	public RespostaDTO() {
	}



	public RespostaDTO(Long idResposta, Long idUsuario, Long idPergunta, Long idSala, String alternativaSelecionada,
			long tempoGasto) {
		this.idResposta = idResposta;
		this.idUsuario = idUsuario;
		this.idPergunta = idPergunta;
		this.idSala = idSala;
		this.alternativaSelecionada = alternativaSelecionada;
		this.tempoGasto = tempoGasto;
	}



	public Long getIdResposta() {
		return idResposta;
	}



	public void setIdResposta(Long idResposta) {
		this.idResposta = idResposta;
	}



	public Long getIdUsuario() {
		return idUsuario;
	}



	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}



	public Long getIdPergunta() {
		return idPergunta;
	}



	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}



	public Long getIdSala() {
		return idSala;
	}



	public void setIdSala(Long idSala) {
		this.idSala = idSala;
	}



	public String getAlternativaSelecionada() {
		return alternativaSelecionada;
	}



	public void setAlternativaSelecionada(String alternativaSelecionada) {
		this.alternativaSelecionada = alternativaSelecionada;
	}



	public long getTempoGasto() {
		return tempoGasto;
	}



	public void setTempoGasto(long tempoGasto) {
		this.tempoGasto = tempoGasto;
	}
	
	

}
