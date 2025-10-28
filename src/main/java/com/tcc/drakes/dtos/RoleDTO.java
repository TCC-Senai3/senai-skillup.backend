package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Role; // Importe sua entidade Role

public class RoleDTO {
    
    private Long id;
    private String nome; 

    // Construtor vazio
    public RoleDTO() {
    }

    // Construtor a partir da entidade (útil)
    public RoleDTO(Role role) {
        this.id = role.getId();
        this.nome = role.getNome();
    }

    // Getters e Setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }
}