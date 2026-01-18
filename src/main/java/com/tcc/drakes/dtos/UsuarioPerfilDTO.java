package com.tcc.drakes.dtos;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

import com.tcc.drakes.entities.Usuario;

public class UsuarioPerfilDTO {

    // --- 1. ADICIONADO O CAMPO ID ---
    private long id; // Campo para o ID do usuário

    private String nome;
    private String avatar;
    private String biografia;
    private Long pontuacao;
    private boolean online;
    private Set<String> roles; // Mantém o Set<String> para as roles

    // Construtor vazio
    public UsuarioPerfilDTO() {
    }

    public UsuarioPerfilDTO(Usuario usuario) {
        // --- 2. POPULADO O CAMPO ID ---
        this.id = usuario.getId(); // Pega o ID da entidade

        this.nome = usuario.getNome();
        this.avatar = usuario.getAvatar();
        this.biografia = usuario.getBiografia();
        this.pontuacao = usuario.getPontuacao();

        // Popula as roles (como estava antes)
        if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
            this.roles = usuario.getRoles().stream()
                    .map(role -> role.getNome())
                    .collect(Collectors.toSet());
        }

        // Lógica de online (como estava antes)
        this.online = false;
        if (usuario.getDataUltimaAtividade() != null) {
            long minutosDesdeUltimaAtividade = ChronoUnit.MINUTES.between(usuario.getDataUltimaAtividade(),
                    LocalDateTime.now());
            if (minutosDesdeUltimaAtividade <= 5) {
                this.online = true;
            }
        }
    }

    // --- 3. ADICIONADO GETTER E SETTER PARA ID ---
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // --- Getters e Setters existentes ---
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public Long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Long pontuacao) {
        this.pontuacao = pontuacao;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}