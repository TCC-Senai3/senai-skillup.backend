package com.tcc.drakes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "turno_tb")
public class Turno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nmrTurno;
	private String tempoLimite;
	private String tempoUsado;
	private String statusTurno;
	
	public Turno() {
	}

	public Turno(long id, String nmrTurno, String tempoLimite, String tempoUsado, String statusTurno) {
		this.id = id;
		this.nmrTurno = nmrTurno;
		this.tempoLimite = tempoLimite;
		this.tempoUsado = tempoUsado;
		this.statusTurno = statusTurno;
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
