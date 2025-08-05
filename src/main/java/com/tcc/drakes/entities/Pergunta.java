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
	
	// Muitas Perguntas pertencem a um Tema (ManyToOne)
	@ManyToOne
	@JoinColumn(name = "tema_id") // Nome da coluna da chave estrangeira na tabela Pergunta
	private Tema tema;
	
	@ManyToOne
	@JoinColumn(name = "formulario_id") // Nome da coluna da chave estrangeira na tabela Pergunta
	private Formulario formulario; // Novo atributo para a relação com Formulário
	
	// Uma Pergunta pode ter muitas Alternativas (OneToMany)
	@OneToMany(mappedBy = "pergunta", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
	private List<Alternativa> alternativas = new ArrayList<>();
	
	  @OneToMany(mappedBy = "pergunta")
	    private List<Resposta> respostas;

	public Pergunta() {
	}

	// Construtor ajustado para incluir Formulário
	public Pergunta(Long idPergunta, String textoPergunta, Tema tema, Formulario formulario) {
		this.idPergunta = idPergunta;
		this.textoPergunta = textoPergunta;
		this.tema = tema;
		this.formulario = formulario; // Adicionado Formulário ao construtor
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

	public Formulario getFormulario() { // Novo getter
		return formulario;
	}

	public void setFormulario(Formulario formulario) { // Novo setter
		this.formulario = formulario;
	}

	public List<Alternativa> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(List<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}
	
	// Métodos auxiliares para adicionar/remover alternativas
	public void addAlternativa(Alternativa alternativa) {
		this.alternativas.add(alternativa);
		alternativa.setPergunta(this);
	}

	public void removeAlternativa(Alternativa alternativa) {
		this.alternativas.remove(alternativa);
		alternativa.setPergunta(null);
	}
}