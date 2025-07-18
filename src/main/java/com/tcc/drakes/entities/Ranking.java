package com.tcc.drakes.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_ranking")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRanking;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    private Long pontuacao = 0L;


    public Ranking() {
    }

    public Ranking(Usuario usuario, Sala sala) {
        this.usuario = usuario;
        this.sala = sala;
        this.pontuacao = 0L;
    }

    
    
    // Getters e setters

    public Long getIdRanking() {
        return idRanking;
    }

    public void setIdRanking(Long idRanking) {
        this.idRanking = idRanking;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Long pontuacao) {
        this.pontuacao = pontuacao;
    }

   

    // Método utilitário para incrementar pontuação
    public void incrementarPontuacao() {
        this.pontuacao++;
       
    }

	
}
