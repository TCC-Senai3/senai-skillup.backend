package com.tcc.drakes.repositories;

import com.tcc.drakes.entities.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; 

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
 
    Optional<Permissao> findByNome(String nome);
}