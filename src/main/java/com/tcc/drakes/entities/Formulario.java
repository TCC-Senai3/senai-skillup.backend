package com.tcc.drakes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_formulario")
public class Formulario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFormulario;

    private String titulo;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pergunta> perguntas = new ArrayList<>();

    public Formulario() {
    }

    public Formulario(Long idFormulario, String titulo) {
        this.idFormulario = idFormulario;
        this.titulo = titulo;
    }

    public Long getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    
    public void addPergunta(Pergunta pergunta) {
        if (!this.perguntas.contains(pergunta)) {
            this.perguntas.add(pergunta);
            pergunta.setFormulario(this); 
        }
    }

    public void removePergunta(Pergunta pergunta) {
        if (this.perguntas.contains(pergunta)) {
            this.perguntas.remove(pergunta);
            pergunta.setFormulario(null); 
        }
    }
}