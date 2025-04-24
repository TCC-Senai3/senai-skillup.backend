package com.tcc.drakes.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_carta")
public class Carta {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String tipo;
private String efeito;
private String custoEnergia;
private String descricao;
public Carta() {
}


public Carta(long id, String tipo, String efeito, String custoEnergia, String descricao) {
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


public void setId_carta(Long id2) {
	// TODO Auto-generated method stub
	
}
}