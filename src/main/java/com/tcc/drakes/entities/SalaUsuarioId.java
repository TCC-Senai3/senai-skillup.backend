package com.tcc.drakes.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SalaUsuarioId implements Serializable {

    private Long salaId;
    private Long usuarioId;

    // Construtor vazio é obrigatório
    public SalaUsuarioId() {}

    public SalaUsuarioId(Long salaId, Long usuarioId) {
        this.salaId = salaId;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters
    public Long getSalaId() {
        return salaId;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Métodos hashCode e equals são OBRIGATÓRIOS para chaves compostas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaUsuarioId that = (SalaUsuarioId) o;
        return Objects.equals(salaId, that.salaId) && Objects.equals(usuarioId, that.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salaId, usuarioId);
    }
}