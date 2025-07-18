package com.tcc.drakes.services;

import com.tcc.drakes.dtos.SalaDTO;
import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.repositories.SalaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    public List<SalaDTO> findAll() {
        List<Sala> salas = repository.findAll();
        return salas.stream().map(SalaDTO::new).collect(Collectors.toList());
    }

    public SalaDTO findById(Long id) {
        Optional<Sala> sala = repository.findById(id);
        return sala.map(SalaDTO::new).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }

    public SalaDTO insert(SalaDTO dto) {
        Sala sala = new Sala();
        copyDtoToEntity(dto, sala);
        sala = repository.save(sala);
        return new SalaDTO(sala);
    }

    public SalaDTO update(Long id, SalaDTO dto) {
        Sala sala = repository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        copyDtoToEntity(dto, sala);
        sala = repository.save(sala);
        return new SalaDTO(sala);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void copyDtoToEntity(SalaDTO dto, Sala entity) {
        entity.setIdUsuario(dto.getIdUsuario());
        entity.setIdTema(dto.getIdTema());
        entity.setNomeSala(dto.getNomeSala());
        entity.setDataCriacao(dto.getDataCriacao());
        entity.setStatusSala(dto.getStatusSala());
    }
}
