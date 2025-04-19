package com.tcc.drakes.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.entities.Turno;
import com.tcc.drakes.repositories.TurnoRepository;

@RestController
@RequestMapping("/turno")
public class TurnoController {

	
	 @Autowired
	    private TurnoRepository turnoRepository;

	    // Listar todos os turnos
	    @GetMapping
	    public List<Turno> listarTurnos() {
	        return turnoRepository.findAll();
	    }

	    // Buscar turno por ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Turno> buscarPorId(@PathVariable Long id) {
	        Optional<Turno> turno = turnoRepository.findById(id);
	        return turno.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	    }

	    // Criar novo turno
	    @PostMapping
	    public Turno criarTurno(@RequestBody Turno turno) {
	        return turnoRepository.save(turno);
	    }

	    // Atualizar turno existente
	    @PutMapping("/{id}")
	    public ResponseEntity<Turno> atualizarTurno(@PathVariable Long id, @RequestBody Turno dadosAtualizados) {
	        Optional<Turno> turnoExistente = turnoRepository.findById(id);

	        if (turnoExistente.isPresent()) {
	            Turno turno = turnoExistente.get();
	            turno.setNmrTurno(dadosAtualizados.getNmrTurno());
	            turno.setTempoLimite(dadosAtualizados.getTempoLimite());
	            turno.setTempoUsado(dadosAtualizados.getTempoUsado());
	            turno.setStatusTurno(dadosAtualizados.getStatusTurno());

	            return ResponseEntity.ok(turnoRepository.save(turno));
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    // Deletar turno
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deletarTurno(@PathVariable Long id) {
	        if (turnoRepository.existsById(id)) {
	            turnoRepository.deleteById(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
}
