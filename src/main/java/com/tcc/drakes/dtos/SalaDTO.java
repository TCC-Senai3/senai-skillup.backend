package com.tcc.drakes.dtos;

import java.time.LocalDate;

import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.StatusSala;

public class SalaDTO {

    private Long idSala;
    private Long idUsuario;
    private Long idTema;
    private String nomeSala;
    private LocalDate dataCriacao;
    private StatusSala statusSala;

    public SalaDTO() {
    }

    public SalaDTO(Sala entity) {
        this.idSala = entity.getIdSala();
        this.idUsuario = entity.getIdUsuario();
        this.idTema = entity.getIdTema();
        this.nomeSala = entity.getNomeSala();
        this.dataCriacao = entity.getDataCriacao();
        this.statusSala = entity.getStatusSala();
    }

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdTema() {
        return idTema;
    }

    public void setIdTema(Long idTema) {
        this.idTema = idTema;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusSala getStatusSala() {
        return statusSala;
    }

    public void setStatusSala(StatusSala statusSala) {
        this.statusSala = statusSala;
    }
}
