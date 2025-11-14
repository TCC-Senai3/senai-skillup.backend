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

    // ✅ CORREÇÃO 1: Adicionado r.usuario.avatar no SELECT e no GROUP BY
    @Query("SELECT r.usuario.nome, SUM(r.pontuacao), r.usuario.avatar FROM Ranking r GROUP BY r.usuario.nome, r.usuario.avatar ORDER BY SUM(r.pontuacao) DESC")
    List<Object[]> getRankingGeral();

    // ✅ CORREÇÃO 2: Adicionado r.usuario.avatar no SELECT
    @Query("SELECT r.usuario.nome, r.pontuacao, r.usuario.avatar FROM Ranking r WHERE r.sala.id = :salaId ORDER BY r.pontuacao DESC")
    List<Object[]> getRankingPorSala(Long salaId);
}