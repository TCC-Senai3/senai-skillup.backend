package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Formulario;
import java.util.List;
import java.util.stream.Collectors;

public class FormularioDTO {

    private Long idFormulario;
    private String titulo;

    // Lista de PerguntaDTOs para representar as perguntas associadas a este formulário
    private List<PerguntaDTO> perguntas;

    public FormularioDTO() {
    }

    // Construtor que mapeia a entidade Formulario para o DTO, incluindo perguntas com seus dados completos
    public FormularioDTO(Formulario entity) {
        this.idFormulario = entity.getIdFormulario();
        this.titulo = entity.getTitulo();

        if (entity.getPerguntas() != null) {
            this.perguntas = entity.getPerguntas().stream()
                // Usando o construtor que recebe a entidade Pergunta para garantir que tema e idFormulario sejam mapeados corretamente
                .map(PerguntaDTO::new)
                .collect(Collectors.toList());
        }
    }

    // Construtor completo
    public FormularioDTO(Long idFormulario, String titulo, List<PerguntaDTO> perguntas) {
        this.idFormulario = idFormulario;
        this.titulo = titulo;
        this.perguntas = perguntas;
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

    public List<PerguntaDTO> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<PerguntaDTO> perguntas) {
        this.perguntas = perguntas;
    }
}
