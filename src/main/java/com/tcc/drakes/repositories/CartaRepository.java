package com.tcc.drakes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event.ID;

import com.tcc.drakes.entities.Carta;

public interface CartaRepository  extends JpaRepository<Carta, ID>{

	Optional<Carta> findAllById(Long id);

	boolean existsById(Long id);

	void deleteById(Long id); 

}