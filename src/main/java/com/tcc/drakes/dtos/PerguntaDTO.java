package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Pergunta;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class PerguntaDTO{

	private Long idPergunta;
	
	private Long idAlternativa;
	private String textoPergunta;
	
	boolean respostaCorreta;
	
	private Long idTema;
	
	
	public PerguntaDTO(Pergunta entity) {
		idPergunta = entity.getIdPergunta();
		idAlternativa = entity.getIdAlternativa();
		textoPergunta = entity.getTextoPergunta();
		respostaCorreta = entity.isRespostaCorreta();
		idTema = entity.getIdTema();
	}
	

	public PerguntaDTO(Long idPergunta, Long idAlternativa, String textoPergunta, boolean respostaCorreta,
			Long idTema) {
		this.idPergunta = idPergunta;
		this.idAlternativa = idAlternativa;
		this.textoPergunta = textoPergunta;
		this.respostaCorreta = respostaCorreta;
		this.idTema = idTema;
	}

	public PerguntaDTO() {
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
