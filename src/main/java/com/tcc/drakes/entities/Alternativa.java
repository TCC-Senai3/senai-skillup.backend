package com.tcc.drakes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_alternativa")
public class Alternativa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAlternativa;
	
	@ManyToOne
	@JoinColumn(name = "pergunta_id") 
	private Pergunta pergunta;
	
	private String textoAlternativa; 
	
	private boolean correta; 

	public Alternativa() {
	}

	
	public Alternativa(Long idAlternativa, Pergunta pergunta, String textoAlternativa, boolean correta) {
		this.idAlternativa = idAlternativa;
		this.pergunta = pergunta;
		this.textoAlternativa = textoAlternativa;
		this.correta = correta;
	}

	public Long getIdAlternativa() {
		return idAlternativa;
	}

	public void setIdAlternativa(Long idAlternativa) {
		this.idAlternativa = idAlternativa;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public String getTextoAlternativa() {
		return textoAlternativa;
	}

	public void setTextoAlternativa(String textoAlternativa) {
		this.textoAlternativa = textoAlternativa;
	}

	public boolean isCorreta() {
		return correta;
	}

	public void setCorreta(boolean correta) {
		this.correta = correta;
	}
}