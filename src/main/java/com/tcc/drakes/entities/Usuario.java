package com.tcc.drakes.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nome;
	private String email;
	private String senha;
	private String biografia;
//	private TipoUsuario tipoUsuario;
	
	public Usuario() {}

	public Usuario(long id, String nome, String email, String senha, String biografia) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.biografia = biografia;
		//this.tipoUsuario = tipoUsuario;
	}
	
	 @OneToMany(mappedBy = "usuario")
	    private List<Resposta> respostas;

	public long getId() {
		return id;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

//	public TipoUsuario getTipoUsuario() {
//		return tipoUsuario;
//	}
//
//	public void setTipoUsuario(TipoUsuario tipoUsuario) {
	//	this.tipoUsuario = tipoUsuario;
//	}
	
	
	
	
	

}
