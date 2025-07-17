package com.tcc.drakes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcc.drakes.entities.Formulario;

@Repository
public interface FormularioRepository extends JpaRepository<Formulario, Long> {

}