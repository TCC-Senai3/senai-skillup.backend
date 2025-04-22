package com.tcc.drakes.controllers;

import com.tcc.drakes.entities.Pergunta;
import com.tcc.drakes.services.PerguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perguntas")
public class PerguntaController {

    @Autowired
    private PerguntaService perguntaService;

    // Criar uma nova pergunta
    @PostMapping
    public ResponseEntity<Pergunta> criarPergunta(@RequestBody Pergunta pergunta) {
        Pergunta novaPergunta = perguntaService.criarPergunta(pergunta);
        return ResponseEntity.ok(novaPergunta);
    }

    // Obter todas as perguntas
    @GetMapping
    public List<Pergunta> listarPerguntas() {
        return perguntaService.listarPerguntas();
    }

    // Obter uma pergunta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> obterPerguntaPorId(@PathVariable Long id) {
        Optional<Pergunta> pergunta = perguntaService.obterPerguntaPorId(id);
        return pergunta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar uma pergunta
    @PutMapping("/{id}")
    public ResponseEntity<Pergunta> atualizarPergunta(@PathVariable Long id, @RequestBody Pergunta pergunta) {
        Pergunta perguntaAtualizada = perguntaService.atualizarPergunta(id, pergunta);
        return perguntaAtualizada != null ? ResponseEntity.ok(perguntaAtualizada) : ResponseEntity.notFound().build();
    }

    // Deletar uma pergunta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPergunta(@PathVariable Long id) {
        return perguntaService.deletarPergunta(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
