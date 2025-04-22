
package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Carta;

public class CartaDTO {
	
	private long id;

	private String tipo;
	private String efeito;
	private String custoEnergia;
	private String descricao;



public CartaDTO() {
}

public CartaDTO(Carta entity) {
	id = entity.getId();
	tipo = entity.getTipo();
	efeito = entity.getEfeito();
	custoEnergia = entity.getCustoEnergia();
	descricao = entity.getDescricao();
}

public CartaDTO(long id, String tipo, String efeito, String custoEnergia, String descricao) {
	this.id = id;
	this.tipo = tipo;
	this.efeito = efeito;
	this.custoEnergia = custoEnergia;
	this.descricao = descricao;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getTipo() {
	return tipo;
}
public void setTipo(String tipo) {
	this.tipo = tipo;
}
public String getEfeito() {
	return efeito;
}
public void setEfeito(String efeito) {
	this.efeito = efeito;
}
public String getCustoEnergia() {
	return custoEnergia;
}
public void setCustoEnergia(String custoEnergia) {
	this.custoEnergia = custoEnergia;
}
public String getDescricao() {
	return descricao;
}
public void setDescricao(String descricao) {
	this.descricao = descricao;
}


}

