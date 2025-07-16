package com.tcc.drakes.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tema")
public class Tema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTema;
	
	private String nomeTema;
	
	// Um Tema pode ter muitas Perguntas (OneToMany)
	// mappedBy indica o nome do atributo na classe Pergunta que mapeia esta relação
	// CascadeType.ALL significa que operações como persistir, remover, etc., em Tema
	// serão propagadas para as Perguntas associadas. CUIDADO ao usar ALL, avalie seu caso de uso.
	// orphanRemoval = true garante que se uma Pergunta for desassociada de um Tema, ela será removida do banco.
	@OneToMany(mappedBy = "tema", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
	private List<Pergunta> perguntas = new ArrayList<>(); // Inicialize para evitar NullPointerException
	
	public Tema(Long idTema, String nomeTema) { // Construtor ajustado
		this.idTema = idTema;
		this.nomeTema = nomeTema;
	}

	public Tema() {
	}

	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
	}

	public String getNomeTema() {
		return nomeTema;
	}

	public void setNomeTema(String nomeTema) {
		this.nomeTema = nomeTema;
	}

	public List<Pergunta> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}

	// Métodos auxiliares para adicionar/remover perguntas, garantindo a bidirecionalidade
	public void addPergunta(Pergunta pergunta) {
		this.perguntas.add(pergunta);
		pergunta.setTema(this); // Garante que a pergunta saiba qual é o seu tema
	}

	public void removePergunta(Pergunta pergunta) {
		this.perguntas.remove(pergunta);
		pergunta.setTema(null); // Desassocia a pergunta do tema
	}
}