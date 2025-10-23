package com.tcc.drakes.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_sala")
public class Sala {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSala;

	@Column(name = "codigo_sala", unique = true, nullable = false, length = 6)
	private String codigoSala;

	private Long idUsuario;
	private Long idTema;
	private String nomeSala;
	private LocalDate dataCriacao;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_formulario")
	private Formulario formulario;

	@Enumerated(EnumType.STRING)
	private StatusSala statusSala;
	

	@OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SalaUsuario> participantes = new HashSet<>();

	public Sala() {
	}

	// Getters e Setters
	public Long getIdSala() {
		return idSala;
	}

	public void setIdSala(Long idSala) {
		this.idSala = idSala;
	}

	public String getCodigoSala() {
		return codigoSala;
	}

	public void setCodigoSala(String codigoSala) {
		this.codigoSala = codigoSala;
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

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public StatusSala getStatusSala() {
		return statusSala;
	}

	public void setStatusSala(StatusSala statusSala) {
		this.statusSala = statusSala;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}
	
	// GETTERS E SETTERS CORRIGIDOS
	public Set<SalaUsuario> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(Set<SalaUsuario> participantes) {
		this.participantes = participantes;
	}
}