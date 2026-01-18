package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Usuario;

public class RelatorioUsuarioDTO {

    private Long id;
    private String nome;
    private String email;

    public RelatorioUsuarioDTO(Usuario entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail();

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

}
