
package com.tcc.drakes.dtos;

public class UsuarioListaDTO {

    private Long id;
    private String nome;
    private Long pontuacao;
    private String avatar;

    public UsuarioListaDTO(Long id, String nome, Long pontuacao, String avatar) {
        this.id = id;
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.avatar = avatar;
    }
    
    
    
    public String getAvatar() {
		return avatar;
	}



	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Long pontuacao) {
        this.pontuacao = pontuacao;
    }
}