package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Turno;

public class TurnoDTO {
	
private long id;
	
	private String nmrTurno;
	private String tempoLimite;
	private String tempoUsado;
	private String statusTurno;
	
	public TurnoDTO() {
	}

	public TurnoDTO(long id, String nmrTurno, String tempoLimite, String tempoUsado, String statusTurno) {
		this.id = id;
		this.nmrTurno = nmrTurno;
		this.tempoLimite = tempoLimite;
		this.tempoUsado = tempoUsado;
		this.statusTurno = statusTurno;
	}
	
	public TurnoDTO(Turno entity) {
		id = entity.getId();
		nmrTurno = entity.getNmrTurno();
		tempoLimite = entity.getTempoLimite();
		tempoUsado = entity.getTempoLimite();
		statusTurno = entity.getNmrTurno();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNmrTurno() {
		return nmrTurno;
	}

	public void setNmrTurno(String nmrTurno) {
		this.nmrTurno = nmrTurno;
	}

	public String getTempoLimite() {
		return tempoLimite;
	}

	public void setTempoLimite(String tempoLimite) {
		this.tempoLimite = tempoLimite;
	}

	public String getTempoUsado() {
		return tempoUsado;
	}

	public void setTempoUsado(String tempoUsado) {
		this.tempoUsado = tempoUsado;
	}

	public String getStatusTurno() {
		return statusTurno;
	}

	public void setStatusTurno(String statusTurno) {
		this.statusTurno = statusTurno;
	}
	
	
	

}
