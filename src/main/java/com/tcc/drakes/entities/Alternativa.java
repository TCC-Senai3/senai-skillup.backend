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
	
	// Muitas Alternativas pertencem a uma Pergunta (ManyToOne)
	@ManyToOne
	@JoinColumn(name = "pergunta_id") // Nome da coluna da chave estrangeira na tabela Alternativa
	private Pergunta pergunta;
	
	private String textoAlternativa; // Uma única alternativa com seu texto
	
	private boolean correta; // Indica se esta alternativa é a resposta correta para a pergunta

	public Alternativa() {
	}

	// Construtor ajustado
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