package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Alternativa;

public class AlternativaDTO {

	private Long idAlternativa;

	private Long idPergunta;

	private String textoAlternativa;
	private boolean correta;

	public AlternativaDTO(Alternativa entity) {
		this.idAlternativa = entity.getIdAlternativa();
		if (entity.getPergunta() != null) {
			this.idPergunta = entity.getPergunta().getIdPergunta();
		}
		this.textoAlternativa = entity.getTextoAlternativa();
		this.correta = entity.isCorreta();
	}

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