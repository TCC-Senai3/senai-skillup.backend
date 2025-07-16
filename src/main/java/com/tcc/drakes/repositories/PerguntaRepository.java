package com.tcc.drakes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcc.drakes.entities.Pergunta;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    List<Pergunta> findByTemaIdTema(Long temaId); // Método para buscar perguntas por ID do tema
}