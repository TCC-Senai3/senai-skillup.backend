package com.tcc.drakes.repositories;

import com.tcc.drakes.entities.Ranking;
import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    List<Ranking> findBySala(Sala sala);

    Ranking findByUsuarioAndSala(Usuario usuario, Sala sala);

    @Query("SELECT r.usuario.nome, SUM(r.pontuacao) FROM Ranking r GROUP BY r.usuario ORDER BY SUM(r.pontuacao) DESC")
    List<Object[]> getRankingGeral();

    @Query("SELECT r.usuario.nome, r.pontuacao FROM Ranking r WHERE r.sala.id = :salaId ORDER BY r.pontuacao DESC")
    List<Object[]> getRankingPorSala(Long salaId);
}
