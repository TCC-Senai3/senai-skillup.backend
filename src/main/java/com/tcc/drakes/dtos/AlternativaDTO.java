package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Alternativa;

public class AlternativaDTO {
	
	private Long idAlternativa;
	
	// Referência ao DTO da Pergunta (ou apenas o ID, dependendo da sua necessidade)
	private Long idPergunta; // Para este DTO, o ID da Pergunta é suficiente, evitando carregamento excessivo
	// private PerguntaDTO pergunta; // Poderia ser assim se você precisasse dos detalhes da pergunta na alternativa
	
	private String textoAlternativa; // Uma única alternativa com seu texto
	private boolean correta; // Indica se esta alternativa é a resposta correta para a pergunta
	
	public AlternativaDTO(Alternativa entity) {
		this.idAlternativa = entity.getIdAlternativa();
		if (entity.getPergunta() != null) {
			this.idPergunta = entity.getPergunta().getIdPergunta();
		}
		this.textoAlternativa = entity.getTextoAlternativa();
		this.correta = entity.isCorreta();
	}

	// Construtor completo ajustado
	public AlternativaDTO(Long idAlternativa, Long idPergunta, String textoAlternativa, boolean correta) {
		this.idAlternativa = idAlternativa;
		this.idPergunta = idPergunta;
		this.textoAlternativa = textoAlternativa;
		this.correta = correta;
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