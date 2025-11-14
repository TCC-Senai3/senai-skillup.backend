package com.tcc.drakes.entities;

import jakarta.persistence.*;

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
    
    private String avatar;

    // 1. Construtor Vazio (Obrigatório JPA)
    public Ranking() {
    }

    // 2. ✅ ADICIONE ESTE CONSTRUTOR (Para resolver o erro do Service)
    public Ranking(Usuario usuario, Sala sala) {
        this.usuario = usuario;
        this.sala = sala;
        this.pontuacao = 0L;
        // Pega o avatar atual do usuário automaticamente ao criar o ranking
        this.avatar = usuario.getAvatar(); 
    }

    // 3. Construtor Completo (Você já tinha este)
    public Ranking(Usuario usuario, Sala sala, String avatar) {
        this.usuario = usuario;
        this.sala = sala;
        this.pontuacao = 0L;
        this.avatar = avatar;
    }

    // Getters e setters...
    public Long getIdRanking() { return idRanking; }
    public void setIdRanking(Long idRanking) { this.idRanking = idRanking; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    
    public Long getPontuacao() { return pontuacao; }
    public void setPontuacao(Long pontuacao) { this.pontuacao = pontuacao; }
    
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public void adicionarPontos(Long pontos) {
        if (pontos > 0) {
            this.pontuacao += pontos;
        }
    }
}