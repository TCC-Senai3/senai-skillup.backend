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

import com.tcc.drakes.dtos.TemaDTO;
import com.tcc.drakes.services.TemaService;

@RestController
@RequestMapping("/temas") 
public class TemaController {
	
	@Autowired
	private TemaService temaService;
	
	@PostMapping
	public ResponseEntity<TemaDTO> cadastrarTema(@RequestBody TemaDTO temaDTO) {
	    TemaDTO novoTema = temaService.criarTema(temaDTO);
	    return ResponseEntity.status(HttpStatus.CREATED).body(novoTema); 
	}

	@GetMapping
	public ResponseEntity<List<TemaDTO>> listarTodosTemas() {
		List<TemaDTO> temas = temaService.findAllTemas();
		return ResponseEntity.ok(temas); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TemaDTO> buscarTemaPorId(@PathVariable Long id) {
	    try {
	        TemaDTO tema = temaService.findTemaById(id);
	        return ResponseEntity.ok(tema); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TemaDTO> atualizarTema(@PathVariable Long id, @RequestBody TemaDTO temaDTO) {
	    try {
	        TemaDTO temaAtualizado = temaService.updateTema(id, temaDTO);
	        return ResponseEntity.ok(temaAtualizado); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarTema(@PathVariable Long id) {
	    try {
	        temaService.deleteTema(id);
	        return ResponseEntity.noContent().build(); 
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build(); 
	    }
	}
}