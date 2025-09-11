package com.tcc.drakes.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name= "tb_pergunta")
public class Pergunta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPergunta;
	
	private String textoPergunta;
	
	
	@ManyToOne
	@JoinColumn(name = "tema_id")
	private Tema tema;
	
	@ManyToOne
	@JoinColumn(name = "formulario_id") 
	private Formulario formulario; 
	
	
	@OneToMany(mappedBy = "pergunta", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
	private List<Alternativa> alternativas = new ArrayList<>();
	
	  @OneToMany(mappedBy = "pergunta")
	    private List<Resposta> respostas;

	public Pergunta() {
	}


	public Pergunta(Long idPergunta, String textoPergunta, Tema tema, Formulario formulario) {
		this.idPergunta = idPergunta;
		this.textoPergunta = textoPergunta;
		this.tema = tema;
		this.formulario = formulario; 
	}

	public Long getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}

	public String getTextoPergunta() {
		return textoPergunta;
	}

	public void setTextoPergunta(String textoPergunta) {
		this.textoPergunta = textoPergunta;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Formulario getFormulario() { 
		return formulario;
	}

	public void setFormulario(Formulario formulario) { 
		this.formulario = formulario;
	}

	public List<Alternativa> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(List<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}
	

	public void addAlternativa(Alternativa alternativa) {
		this.alternativas.add(alternativa);
		alternativa.setPergunta(this);
	}

	public void removeAlternativa(Alternativa alternativa) {
		this.alternativas.remove(alternativa);
		alternativa.setPergunta(null);
	}
}