package com.tcc.drakes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "tb_resposta")
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idResposta;
	
	private Long idUsuario;
	
	private Long idPergunta;
	
	private Long idSala;
	
	private String alternativaSelecionada;
	
	private long tempoGasto;

	
	
	public Resposta() {
	}



	public Resposta(Long idResposta, Long idUsuario, Long idPergunta, Long idSala, String alternativaSelecionada,
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
