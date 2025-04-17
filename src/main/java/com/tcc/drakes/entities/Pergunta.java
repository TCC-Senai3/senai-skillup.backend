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
	private Long id;
	
	private String textoPergunta;
	
	private String alternativaA;
	private String alternativaB;
	private String alternativaC;
	private String alternativaD;
	
	private String respostaCorreta;
	
	
	
	

	public Pergunta(long id, String textoPergunta, String alternativaA, String alternativaB, String alternativaC,
			String alternativaD, String respostaCorreta) {
		this.id = id;
		this.textoPergunta = textoPergunta;
		this.alternativaA = alternativaA;
		this.alternativaB = alternativaB;
		this.alternativaC = alternativaC;
		this.alternativaD = alternativaD;
		this.respostaCorreta = respostaCorreta;
	}

	public Pergunta() {
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
