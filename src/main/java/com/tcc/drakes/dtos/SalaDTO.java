package com.tcc.drakes.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.StatusSala;

public class SalaDTO {

    private Long idSala;
    private String codigoSala;
    private Long idUsuario;
    private Long idTema;
    private String nomeSala;
    private LocalDate dataCriacao;
    private StatusSala statusSala;
    private Long idFormulario;
    private List<Long> idParticipantes;

    public SalaDTO() {
    }

    public SalaDTO(Sala entity) {
        this.idSala = entity.getIdSala();
        this.codigoSala = entity.getCodigoSala();
        this.idUsuario = entity.getIdUsuario();
        this.idTema = entity.getIdTema();
        this.nomeSala = entity.getNomeSala();
        this.dataCriacao = entity.getDataCriacao();
        this.statusSala = entity.getStatusSala();

        if (entity.getFormulario() != null) {
            this.idFormulario = entity.getFormulario().getIdFormulario();
        }

        // Bloco corrigido
        if (entity.getParticipantes() != null) {
            this.idParticipantes = entity.getParticipantes()
                    .stream()
                    .map(salaUsuario -> salaUsuario.getUsuario().getId()) // Correção aqui
                    .collect(Collectors.toList());
        }
    }

    // O resto da classe (getters e setters) permanece o mesmo...

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
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

    public Long getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }

    public List<Long> getIdParticipantes() {
        return idParticipantes;
    }

    public void setIdParticipantes(List<Long> idParticipantes) {
        this.idParticipantes = idParticipantes;
    }
}