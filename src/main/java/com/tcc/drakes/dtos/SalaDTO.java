package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.StatusSala;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class SalaDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSala;
	
	private Long idUsuario;
	
	private Long idTema;
	
	private String nomeSala;
	
	private Long localDate;
	
	StatusSala statusSala;
	
	public SalaDTO(Sala entity) {
		idSala = entity.getIdSala();
		idUsuario = entity.getIdUsuario();
		idTema = entity.getIdTema();
		nomeSala = entity.getNomeSala();
		localDate = entity.getLocalDate();
		statusSala = entity.getStatusSala();
	}

	public SalaDTO(Long idSala, Long idUsuario, Long idTema, String nomeSala, Long localDate, StatusSala statusSala) {
		this.idSala = idSala;
		this.idUsuario = idUsuario;
		this.idTema = idTema;
		this.nomeSala = nomeSala;
		this.localDate = localDate;
		this.statusSala = statusSala;
	}

	public SalaDTO() {
	}

	public Long getIdSala() {
		return idSala;
	}

	public void setIdSala(Long idSala) {
		this.idSala = idSala;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
	}

	public String getNomeSala() {
		return nomeSala;
	}

	public void setNomeSala(String nomeSala) {
		this.nomeSala = nomeSala;
	}

	public Long getLocalDate() {
		return localDate;
	}

	public void setLocalDate(Long localDate) {
		this.localDate = localDate;
	}

	public StatusSala getStatusSala() {
		return statusSala;
	}

	public void setStatusSala(StatusSala statusSala) {
		this.statusSala = statusSala;
	}
	
	

}
