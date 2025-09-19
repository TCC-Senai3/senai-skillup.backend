package com.tcc.drakes.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	
	@ManyToMany
	@JoinTable(
	    name = "tb_sala_usuario",
	    joinColumns = @JoinColumn(name = "sala_id"),
	    inverseJoinColumns = @JoinColumn(name = "usuario_id")
	)
	private List<Usuario> participantes = new ArrayList<>();

	public Sala() {
	}

	
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

    public List<Usuario> getParticipantes() {
	    return participantes;
	}

	public void setParticipantes(List<Usuario> participantes) {
	    this.participantes = participantes;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}
}