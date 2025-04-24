package com.tcc.drakes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "carta_jogada_tb")
public class CartaJogada {

    @Id
    private Long id;

    private String idUsuario;
    private String idCarta;
    private String turno;
    private String usadaTurno;

    public CartaJogada() {
    	
    }

	public CartaJogada(Long id, String idUsuario, String idCarta, String turno, String usadaTurno) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idCarta = idCarta;
		this.turno = turno;
		this.usadaTurno = usadaTurno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdCarta() {
		return idCarta;
	}

	public void setIdCarta(String idCarta) {
		this.idCarta = idCarta;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getUsadaTurno() {
		return usadaTurno;
	}

	public void setUsadaTurno(String usadaTurno) {
		this.usadaTurno = usadaTurno;
	}

    
}