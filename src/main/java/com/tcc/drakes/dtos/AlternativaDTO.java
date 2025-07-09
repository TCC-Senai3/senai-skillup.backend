package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Alternativa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AlternativaDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAlternativa;
	
	private Long idPergunta;
	private String alternativaA;
	private String alternativaB;
	private String alternativaC;
	private String alternativaD;
	
	boolean correta;
	
	private String alternativaSelecionada;
	
	public AlternativaDTO(Alternativa entity) {
		idAlternativa = entity.getIdAlternativa();
		idPergunta = entity.getIdPergunta();
		alternativaA = entity.getAlternativaA();
		alternativaB = entity.getAlternativaB();
		alternativaC = entity.getAlternativaC();
		alternativaD = entity.getAlternativaD();
		correta = entity.isCorreta();
		alternativaSelecionada = entity.getAlternativaSelecionada();
	}

	public AlternativaDTO(Long idAlternativa, Long idPergunta, String alternativaA, String alternativaB,
			String alternativaC, String alternativaD, boolean correta, String alternativaSelecionada) {
		this.idAlternativa = idAlternativa;
		this.idPergunta = idPergunta;
		this.alternativaA = alternativaA;
		this.alternativaB = alternativaB;
		this.alternativaC = alternativaC;
		this.alternativaD = alternativaD;
		this.correta = correta;
		this.alternativaSelecionada = alternativaSelecionada;
	}

	public AlternativaDTO() {
	}

	public Long getIdAlternativa() {
		return idAlternativa;
	}

	public void setIdAlternativa(Long idAlternativa) {
		this.idAlternativa = idAlternativa;
	}

	public Long getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
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

	public boolean isCorreta() {
		return correta;
	}

	public void setCorreta(boolean correta) {
		this.correta = correta;
	}

	public String getAlternativaSelecionada() {
		return alternativaSelecionada;
	}

	public void setAlternativaSelecionada(String alternativaSelecionada) {
		this.alternativaSelecionada = alternativaSelecionada;
	}
	
}
