package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Pergunta;

public class PerguntaDTO{
	
	private long id;
	
	private String textoPergunta;
	
	private String alternativaA;
	private String alternativaB;
	private String alternativaC;
	private String alternativaD;
	
	private String respostaCorreta;
	

	public PerguntaDTO(long id, String textoPergunta, String alternativaA, String alternativaB, String alternativaC,
			String alternativaD, String respostaCorreta) {
		this.id = id;
		this.textoPergunta = textoPergunta;
		this.alternativaA = alternativaA;
		this.alternativaB = alternativaB;
		this.alternativaC = alternativaC;
		this.alternativaD = alternativaD;
		this.respostaCorreta = respostaCorreta;
	}
	
	public PerguntaDTO(Pergunta entity) {
		id = entity.getId();
		textoPergunta = entity.getTextoPergunta();
		alternativaA = entity.getAlternativaA();
		alternativaB = entity.getAlternativaB();
		alternativaC = entity.getAlternativaC();
		alternativaD = entity.getAlternativaD();
		respostaCorreta = entity.getRespostaCorreta();
	}

	public PerguntaDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTextoPergunta() {
		return textoPergunta;
	}

	public void setTextoPergunta(String textoPergunta) {
		this.textoPergunta = textoPergunta;
	}

	public String getAlternativaA() {
		return alternativaA;
	}

	public void setAlternativaA(String alternativaA) {
		this.alternativaA = alternativaA;
	}

	public String getAlternativaB() {
		return alternativaB;
	}

	public void setAlternativaB(String alternativaB) {
		this.alternativaB = alternativaB;
	}

	public String getAlternativaC() {
		return alternativaC;
	}

	public void setAlternativaC(String alternativaC) {
		this.alternativaC = alternativaC;
	}

	public String getAlternativaD() {
		return alternativaD;
	}

	public void setAlternativaD(String alternativaD) {
		this.alternativaD = alternativaD;
	}

	public String getRespostaCorreta() {
		return respostaCorreta;
	}

	public void setRespostaCorreta(String respostaCorreta) {
		this.respostaCorreta = respostaCorreta;
	}
	
	
}
