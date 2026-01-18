package com.tcc.drakes.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcc.drakes.entities.Resposta;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {}