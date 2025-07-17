package com.tcc.drakes.dtos;

import com.tcc.drakes.entities.Pergunta;
import java.util.List;
import java.util.stream.Collectors;

public class PerguntaDTO {

    private Long idPergunta;
    private String textoPergunta;

    // DTO do Tema simplificado
    private TemaDTO tema;

    // Apenas o ID do formulário para evitar recursão
    private Long idFormulario;

    // Lista de alternativas
    private List<AlternativaDTO> alternativas;

    // Construtor baseado na entidade
    public PerguntaDTO(Pergunta entity) {
        this.idPergunta = entity.getIdPergunta();
        this.textoPergunta = entity.getTextoPergunta();

        if (entity.getTema() != null) {
            this.tema = new TemaDTO(entity.getTema().getIdTema(), entity.getTema().getNomeTema(), null);
        }

        if (entity.getFormulario() != null) {
            this.idFormulario = entity.getFormulario().getIdFormulario();
        }

        if (entity.getAlternativas() != null) {
            this.alternativas = entity.getAlternativas().stream()
                    .map(AlternativaDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // Construtor completo
    public PerguntaDTO(Long idPergunta, String textoPergunta, TemaDTO tema, Long idFormulario, List<AlternativaDTO> alternativas) {
        this.idPergunta = idPergunta;
        this.textoPergunta = textoPergunta;
        this.tema = tema;
        this.idFormulario = idFormulario;
        this.alternativas = alternativas;
    }

    public PerguntaDTO() {
    }

    public Long getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(Long idPergunta) {
        this.idPergunta = idPergunta;
    }

    public String getTextoPergunta() {
        return textoPergunta;
    }

    public void setTextoPergunta(String textoPergunta) {
        this.textoPergunta = textoPergunta;
    }

    public TemaDTO getTema() {
        return tema;
    }

    public void setTema(TemaDTO tema) {
        this.tema = tema;
    }

    public Long getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Long idFormulario) {
        this.idFormulario = idFormulario;
    }

    public List<AlternativaDTO> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<AlternativaDTO> alternativas) {
        this.alternativas = alternativas;
    }
}
