package com.tcc.drakes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "pergunta_tb")
public class Pergunta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPergunta;
	
	private Long idAlternativa;
	private String textoPergunta;
	
	boolean respostaCorreta;
	
	private Long idTema;
	
	public Pergunta() {
	}

	public Pergunta(Long idPergunta, Long idAlternativa, String textoPergunta, boolean respostaCorreta, Long idTema) {
		this.idPergunta = idPergunta;
		this.idAlternativa = idAlternativa;
		this.textoPergunta = textoPergunta;
		this.respostaCorreta = respostaCorreta;
		this.idTema = idTema;
	}

	public Long getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}

	public Long getIdAlternativa() {
		return idAlternativa;
	}

	public void setIdAlternativa(Long idAlternativa) {
		this.idAlternativa = idAlternativa;
	}

	public String getTextoPergunta() {
		return textoPergunta;
	}

	public void setTextoPergunta(String textoPergunta) {
		this.textoPergunta = textoPergunta;
	}

	public boolean isRespostaCorreta() {
		return respostaCorreta;
	}

	public void setRespostaCorreta(boolean respostaCorreta) {
		this.respostaCorreta = respostaCorreta;
	}

	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
	}
	
	
	
	
	
}