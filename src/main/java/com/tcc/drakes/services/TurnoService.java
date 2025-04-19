package com.tcc.drakes.services;


import com.tcc.drakes.entities.Turno;
import com.tcc.drakes.repositories.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    // Buscar todos os turnos
    public List<Turno> listarTodos() {
        return turnoRepository.findAll();
    }

    // Buscar um turno por ID
    public Optional<Turno> buscarPorId(Long id) {
        return turnoRepository.findById(id);
    }

    // Criar novo turno
    public Turno criarTurno(Turno turno) {
        return turnoRepository.save(turno);
    }

    // Atualizar turno existente
    public Optional<Turno> atualizarTurno(Long id, Turno novosDados) {
        return turnoRepository.findById(id).map(turno -> {
            turno.setNmrTurno(novosDados.getNmrTurno());
            turno.setTempoLimite(novosDados.getTempoLimite());
            turno.setTempoUsado(novosDados.getTempoUsado());
            turno.setStatusTurno(novosDados.getStatusTurno());
            return turnoRepository.save(turno);
        });
    }

    // Deletar turno
    public boolean deletarTurno(Long id) {
        if (turnoRepository.existsById(id)) {
            turnoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
