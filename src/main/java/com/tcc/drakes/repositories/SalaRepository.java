package com.tcc.drakes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.drakes.entities.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByCodigoSala(String codigoSala);
    Optional<Sala> findByCodigoSala(String codigoSala);
}
