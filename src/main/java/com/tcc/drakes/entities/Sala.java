package com.tcc.drakes.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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

	private Long idUsuario;

	private Long idTema;

	private String nomeSala;

	private LocalDate dataCriacao;
	
	@OneToOne(cascade = CascadeType.ALL) // Cria e persiste o formulário junto com a sala
	@JoinColumn(name = "id_formulario")
	private Formulario formulario;

	@Enumerated(EnumType.STRING)
	private StatusSala statusSala;

	public Sala() {
	}

	public Sala(Long idSala, Long idUsuario, Long idTema, String nomeSala, LocalDate dataCriacao,
			StatusSala statusSala) {
		this.idSala = idSala;
		this.idUsuario = idUsuario;
		this.idTema = idTema;
		this.nomeSala = nomeSala;
		this.dataCriacao = dataCriacao;
		this.statusSala = statusSala;
	}
	
	@ManyToMany
	@JoinTable(
	    name = "tb_sala_usuario",
	    joinColumns = @JoinColumn(name = "sala_id"),
	    inverseJoinColumns = @JoinColumn(name = "usuario_id")
	)
	private List<Usuario> participantes = new ArrayList<>();

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
}
