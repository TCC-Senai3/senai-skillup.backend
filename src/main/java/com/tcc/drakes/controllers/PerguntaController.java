package com.tcc.drakes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.dtos.PerguntaDTO;
import com.tcc.drakes.services.PerguntaService;

@RestController
@RequestMapping("/perguntas") 
public class PerguntaController {
	
	@Autowired
	private PerguntaService perguntaService;
	
	@PostMapping
	public ResponseEntity<PerguntaDTO> cadastrarPergunta(@RequestBody PerguntaDTO perguntaDTO) {
	    try {
	        PerguntaDTO novaPergunta = perguntaService.criarPergunta(perguntaDTO);
	        return ResponseEntity.status(HttpStatus.CREATED).body(novaPergunta); 
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().build();
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
	    }
	}

	@GetMapping
	public ResponseEntity<List<PerguntaDTO>> listarTodasPerguntas() {
		List<PerguntaDTO> perguntas = perguntaService.findAllPerguntas();
		return ResponseEntity.ok(perguntas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PerguntaDTO> buscarPerguntaPorId(@PathVariable Long id) {
	    try {
	        PerguntaDTO pergunta = perguntaService.findPerguntaById(id);
	        return ResponseEntity.ok(pergunta); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
	
	@GetMapping("/por-tema/{temaId}")
	public ResponseEntity<List<PerguntaDTO>> buscarPerguntasPorTema(@PathVariable Long temaId) {
	    try {
	        List<PerguntaDTO> perguntas = perguntaService.findPerguntasByTemaId(temaId);
	        return ResponseEntity.ok(perguntas); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PerguntaDTO> atualizarPergunta(@PathVariable Long id, @RequestBody PerguntaDTO perguntaDTO) {
	    try {
	        PerguntaDTO perguntaAtualizada = perguntaService.updatePergunta(id, perguntaDTO);
	        return ResponseEntity.ok(perguntaAtualizada); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPergunta(@PathVariable Long id) {
	    try {
	        perguntaService.deletePergunta(id);
	        return ResponseEntity.noContent().build(); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
}