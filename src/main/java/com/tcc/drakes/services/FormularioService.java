package com.tcc.drakes.services;

import com.tcc.drakes.dtos.FormularioDTO;
import com.tcc.drakes.entities.Formulario;
import com.tcc.drakes.entities.Pergunta;
import com.tcc.drakes.repositories.FormularioRepository;
import com.tcc.drakes.repositories.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormularioService {

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Transactional(readOnly = true)
    public List<FormularioDTO> findAll() {
        List<Formulario> formularios = formularioRepository.findAll();
        return formularios.stream().map(FormularioDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FormularioDTO findById(Long id) {
        Formulario formulario = formularioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));
        return new FormularioDTO(formulario);
    }

    @Transactional
    public FormularioDTO create(FormularioDTO dto) {
        Formulario entity = new Formulario();
        entity.setTitulo(dto.getTitulo());
        formularioRepository.save(entity);
        return new FormularioDTO(entity);
    }

    @Transactional
    public FormularioDTO associarPerguntaExistente(Long idFormulario, Long idPergunta) {
        Formulario formulario = formularioRepository.findById(idFormulario)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));

        Pergunta pergunta = perguntaRepository.findById(idPergunta)
                .orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));

        pergunta.setFormulario(formulario); // faz a associação
        perguntaRepository.save(pergunta);

        return new FormularioDTO(formulario);
    }

    @Transactional
    public void delete(Long id) {
        if (!formularioRepository.existsById(id)) {
            throw new RuntimeException("Formulário não encontrado");
        }
        formularioRepository.deleteById(id);
    }
}
