package com.tcc.drakes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.drakes.entities.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    // Este método permite buscar um token pelo seu valor em String
    Optional<PasswordResetToken> findByToken(String token);
}