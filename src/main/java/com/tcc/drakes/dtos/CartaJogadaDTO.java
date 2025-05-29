package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.CartaJogada;

public class CartaJogadaDTO {

    private long id;
    private String idUsuario;
    private String idCarta;
    private String turno;
    private String usadaTurno;

    public CartaJogadaDTO() {
    	
    }
    
    
    public CartaJogadaDTO(CartaJogada entity) {
		id = entity.getId();
		idUsuario = entity.getIdUsuario();
		idCarta = entity.getIdCarta();
		turno = entity.getTurno();
		usadaTurno = entity.getUsadaTurno();
	}


	public CartaJogadaDTO(long id, String idUsuario, String idCarta, String turno, String usadaTurno) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idCarta = idCarta;
        this.turno = turno;
        this.usadaTurno = usadaTurno;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
