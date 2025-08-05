package com.tcc.drakes.dtos;

public class RankingDTO {
    private String nomeUsuario;
    private Long pontuacao;

    public RankingDTO() {}

    public RankingDTO(String nomeUsuario, Long pontuacao) {
        this.nomeUsuario = nomeUsuario;
        this.pontuacao = pontuacao;
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
