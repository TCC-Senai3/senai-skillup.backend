package com.tcc.drakes.services;

import com.tcc.drakes.entities.Pergunta;
import com.tcc.drakes.repositories.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerguntaService {

    @Autowired
    private PerguntaRepository perguntaRepository;

    // Criar uma nova pergunta
    public Pergunta criarPergunta(Pergunta pergunta) {
        return perguntaRepository.save(pergunta);
    }

    // Obter todas as perguntas
    public List<Pergunta> listarPerguntas() {
        return perguntaRepository.findAll();
    }

    // Obter uma pergunta por ID
    public Optional<Pergunta> obterPerguntaPorId(Long id) {
        return perguntaRepository.findById(id);
    }

    // Atualizar uma pergunta
    public Pergunta atualizarPergunta(Long id, Pergunta perguntaAtualizada) {
        if (perguntaRepository.existsById(id)) {
            perguntaAtualizada.setId(id); // Certificar que o ID da pergunta é o mesmo
            return perguntaRepository.save(perguntaAtualizada);
        }
        return null; // ou lançar uma exceção
    }

    // Deletar uma pergunta
    public boolean deletarPergunta(Long id) {
        if (perguntaRepository.existsById(id)) {
            perguntaRepository.deleteById(id);
            return true;
        }
        return false; // ou lançar uma exceção
    }
}
