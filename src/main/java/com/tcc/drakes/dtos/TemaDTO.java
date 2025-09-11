package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Tema;
import java.util.List;
import java.util.stream.Collectors;

public class TemaDTO {
	
	private Long idTema;
	private String nomeTema;
	
	private List<PerguntaDTO> perguntas; 
	
	public TemaDTO(Tema entity) {
		this.idTema = entity.getIdTema();
		this.nomeTema = entity.getNomeTema();

		if (entity.getPerguntas() != null) {
			this.perguntas = entity.getPerguntas().stream().map(PerguntaDTO::new).collect(Collectors.toList());
		}
	}

	
	public TemaDTO(Long idTema, String nomeTema, List<PerguntaDTO> perguntas) {
		this.idTema = idTema;
		this.nomeTema = nomeTema;
		this.perguntas = perguntas;
	}

	public TemaDTO() {
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

	public List<PerguntaDTO> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<PerguntaDTO> perguntas) {
		this.perguntas = perguntas;
	}
}