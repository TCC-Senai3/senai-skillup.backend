package com.tcc.drakes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_resposta")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResposta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;

    // Relacionamento com a Alternativa que o usuário selecionou
    @ManyToOne
    @JoinColumn(name = "alternativa_selecionada_id")
    private Alternativa alternativaSelecionada;

    private Integer tempoGasto;

    private boolean respostaCorreta; // Armazena o resultado da verificação

    public Resposta() {
    }

    public Resposta(Long idResposta, Usuario usuario, Pergunta pergunta, Alternativa alternativaSelecionada, Integer tempoGasto, boolean respostaCorreta) {
        this.idResposta = idResposta;
        this.usuario = usuario;
        this.pergunta = pergunta;
        this.alternativaSelecionada = alternativaSelecionada;
        this.tempoGasto = tempoGasto;
        this.respostaCorreta = respostaCorreta;
    }

    // Getters e Setters
    public Long getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(Long idResposta) {
        this.idResposta = idResposta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public Alternativa getAlternativaSelecionada() {
        return alternativaSelecionada;
    }

    public void setAlternativaSelecionada(Alternativa alternativaSelecionada) {
        this.alternativaSelecionada = alternativaSelecionada;
    }

    public Integer getTempoGasto() {
        return tempoGasto;
    }

    public void setTempoGasto(Integer tempoGasto) {
        this.tempoGasto = tempoGasto;
    }

    public boolean isRespostaCorreta() {
        return respostaCorreta;
    }

    public void setRespostaCorreta(boolean respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }
}