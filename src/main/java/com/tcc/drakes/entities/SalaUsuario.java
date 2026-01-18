package com.tcc.drakes.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_sala_usuario")
public class SalaUsuario {

    @EmbeddedId
    private SalaUsuarioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("salaId") // Mapeia o campo 'salaId' da nossa chave (SalaUsuarioId)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId") // Mapeia o campo 'usuarioId' da nossa chave (SalaUsuarioId)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    // Você pode adicionar outros campos aqui se precisar, como data de entrada na sala, etc.

    public SalaUsuario() {}

    public SalaUsuario(Sala sala, Usuario usuario) {
        this.sala = sala;
        this.usuario = usuario;
        this.id = new SalaUsuarioId(sala.getIdSala(), usuario.getId());
    }

    // Getters e Setters
    public SalaUsuarioId getId() {
        return id;
    }

    public void setId(SalaUsuarioId id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}