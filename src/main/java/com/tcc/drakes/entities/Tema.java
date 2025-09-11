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
	
	@OneToMany(mappedBy = "tema", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
	private List<Pergunta> perguntas = new ArrayList<>(); 
	
	public Tema(Long idTema, String nomeTema) {
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

	
	public void addPergunta(Pergunta pergunta) {
		this.perguntas.add(pergunta);
		pergunta.setTema(this); 
	}

	public void removePergunta(Pergunta pergunta) {
		this.perguntas.remove(pergunta);
		pergunta.setTema(null); 
	}
}