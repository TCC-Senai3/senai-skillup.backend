package com.tcc.drakes.dtos;

public class RankingDTO {
    private String nomeUsuario;
    private Long pontuacao;
    private String avatar;

    public RankingDTO() {}

    public RankingDTO(String nomeUsuario, Long pontuacao, String avatar) {
        this.nomeUsuario = nomeUsuario;
        this.pontuacao = pontuacao;
        this.avatar = avatar;
        
    }

    public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Long pontuacao) {
        this.pontuacao = pontuacao;
    }
}
