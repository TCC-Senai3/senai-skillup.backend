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

import com.tcc.drakes.dtos.AlternativaDTO;
import com.tcc.drakes.services.AlternativaService;

@RestController
@RequestMapping("/alternativas") 
public class AlternativaController {
	
	@Autowired
	private AlternativaService alternativaService;
	
	@PostMapping
	public ResponseEntity<AlternativaDTO> cadastrarAlternativa(@RequestBody AlternativaDTO alternativaDTO) {
	    try {
	        AlternativaDTO novaAlternativa = alternativaService.criarAlternativa(alternativaDTO);
	        return ResponseEntity.status(HttpStatus.CREATED).body(novaAlternativa); 
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().build(); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
	    }
	}

	@GetMapping
	public ResponseEntity<List<AlternativaDTO>> listarTodasAlternativas() {
		List<AlternativaDTO> alternativas = alternativaService.findAllAlternativas();
		return ResponseEntity.ok(alternativas); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AlternativaDTO> buscarAlternativaPorId(@PathVariable Long id) {
	    try {
	        AlternativaDTO alternativa = alternativaService.findAlternativaById(id);
	        return ResponseEntity.ok(alternativa); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
	
	@GetMapping("/por-pergunta/{perguntaId}")
	public ResponseEntity<List<AlternativaDTO>> buscarAlternativasPorPergunta(@PathVariable Long perguntaId) {
	    try {
	        List<AlternativaDTO> alternativas = alternativaService.findAlternativasByPerguntaId(perguntaId);
	        return ResponseEntity.ok(alternativas); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AlternativaDTO> atualizarAlternativa(@PathVariable Long id, @RequestBody AlternativaDTO alternativaDTO) {
	    try {
	        AlternativaDTO alternativaAtualizada = alternativaService.updateAlternativa(id, alternativaDTO);
	        return ResponseEntity.ok(alternativaAtualizada); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarAlternativa(@PathVariable Long id) {
	    try {
	        alternativaService.deleteAlternativa(id);
	        return ResponseEntity.noContent().build(); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
}