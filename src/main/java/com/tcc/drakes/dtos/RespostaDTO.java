package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Resposta;

public class RespostaDTO {

    private Long idResposta;
    private Long idUsuario;
    private Long idPergunta;
    private Long idAlternativaSelecionada;
    private Long idSala;
    private Integer tempoGasto;
    private boolean respostaCorreta;

    public RespostaDTO(Resposta entity) {
        this.idResposta = entity.getIdResposta();
        this.idUsuario = entity.getUsuario() != null ? entity.getUsuario().getId() : null;
        this.idPergunta = entity.getPergunta() != null ? entity.getPergunta().getIdPergunta() : null;
        this.idAlternativaSelecionada = entity.getAlternativaSelecionada() != null ? entity.getAlternativaSelecionada().getIdAlternativa() : null;
        // this.idSala = entity.getSala() != null ? entity.getSala().getIdSala() : null; // Descomente se for usar Sala
        this.tempoGasto = entity.getTempoGasto();
        this.respostaCorreta = entity.isRespostaCorreta();
    }

    public RespostaDTO() {
    }

    public RespostaDTO(Long idResposta, Long idUsuario, Long idPergunta, Long idAlternativaSelecionada,
                       Long idSala, Integer tempoGasto, boolean respostaCorreta) {
        this.idResposta = idResposta;
        this.idUsuario = idUsuario;
        this.idPergunta = idPergunta;
        this.idAlternativaSelecionada = idAlternativaSelecionada;
        this.idSala = idSala;
        this.tempoGasto = tempoGasto;
        this.respostaCorreta = respostaCorreta;
    }

    public Long getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(Long idResposta) {
        this.idResposta = idResposta;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(Long idPergunta) {
        this.idPergunta = idPergunta;
    }

    public Long getIdAlternativaSelecionada() {
        return idAlternativaSelecionada;
    }

    public void setIdAlternativaSelecionada(Long idAlternativaSelecionada) {
        this.idAlternativaSelecionada = idAlternativaSelecionada;
    }

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
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