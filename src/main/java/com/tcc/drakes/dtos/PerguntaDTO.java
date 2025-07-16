package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Pergunta;
import java.util.List;
import java.util.stream.Collectors;

public class PerguntaDTO{

	private Long idPergunta;
	private String textoPergunta;
	
	// Referência ao DTO do Tema, pois uma Pergunta pertence a um Tema
	private TemaDTO tema; // Pode ser apenas private Long idTema; se você não precisar dos detalhes completos do tema
	
	// Lista de AlternativaDTOs para representar as alternativas associadas a esta pergunta
	private List<AlternativaDTO> alternativas;
	
	public PerguntaDTO(Pergunta entity) {
		this.idPergunta = entity.getIdPergunta();
		this.textoPergunta = entity.getTextoPergunta();
		// Mapeia o Tema associado para um TemaDTO. Cuidado com ciclos infinitos se TemaDTO também tiver Perguntas.
		// Para evitar isso, TemaDTO para PerguntaDTO pode ser apenas o ID do Tema, ou um construtor de TemaDTO que não carrega Perguntas.
		// Neste exemplo, vou carregar o TemaDTO completo, mas em casos de recursão, você pode usar um TemaSemPerguntasDTO ou apenas o ID.
		if (entity.getTema() != null) {
			// Cria um TemaDTO sem as perguntas para evitar recursão infinita
			this.tema = new TemaDTO(entity.getTema().getIdTema(), entity.getTema().getNomeTema(), null); 
		}
		
		// Mapeia a lista de entidades Alternativa para uma lista de AlternativaDTOs
		if (entity.getAlternativas() != null) {
			this.alternativas = entity.getAlternativas().stream().map(AlternativaDTO::new).collect(Collectors.toList());
		}
	}
	
	// Construtor completo ajustado
	public PerguntaDTO(Long idPergunta, String textoPergunta, TemaDTO tema, List<AlternativaDTO> alternativas) {
		this.idPergunta = idPergunta;
		this.textoPergunta = textoPergunta;
		this.tema = tema;
		this.alternativas = alternativas;
	}

	public PerguntaDTO() {
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

	public TemaDTO getTema() {
		return tema;
	}

	public void setTema(TemaDTO tema) {
		this.tema = tema;
	}

	public List<AlternativaDTO> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(List<AlternativaDTO> alternativas) {
		this.alternativas = alternativas;
	}
}