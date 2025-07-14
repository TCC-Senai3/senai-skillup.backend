package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Tema;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class TemaDTO {
	
	private Long idTema;
	
	private Long idPergunta;
	
	private String nomeTema;
	
	public TemaDTO(Tema entity) {
		idTema = entity.getIdTema();
		idPergunta = entity.getIdPergunta();
		nomeTema = entity.getNomeTema();
	}

	public TemaDTO(Long idTema, Long idPergunta, String nomeTema) {
		this.idTema = idTema;
		this.idPergunta = idPergunta;
		this.nomeTema = nomeTema;
	}

	public TemaDTO() {
	}

	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
	}

	public Long getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}

	public String getNomeTema() {
		return nomeTema;
	}

	public void setNomeTema(String nomeTema) {
		this.nomeTema = nomeTema;
	}
	
	
	

}
