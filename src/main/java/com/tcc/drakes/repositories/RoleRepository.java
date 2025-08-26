package com.tcc.drakes.repositories;

import com.tcc.drakes.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; 

public interface RoleRepository extends JpaRepository<Role, Long> {
   
    Optional<Role> findByNome(String nome);
}