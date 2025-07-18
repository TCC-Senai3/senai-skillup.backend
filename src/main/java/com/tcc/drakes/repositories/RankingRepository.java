//package com.tcc.drakes.repositories;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.tcc.drakes.dtos.RankingDTO;
//import com.tcc.drakes.entities.Ranking;
//import com.tcc.drakes.entities.Sala;
//import com.tcc.drakes.entities.Usuario;
//
//@Repository
//public interface RankingRepository extends JpaRepository<Ranking, Long> {
//
//    // Ranking geral (vitalício): soma total de pontuação por usuário
//    @Query("SELECT new com.tcc.drakes.dtos.RankingDTO(r.usuario.nome, SUM(r.pontuacao)) " +
//           "FROM Ranking r GROUP BY r.usuario.nome ORDER BY SUM(r.pontuacao) DESC")
//    List<RankingDTO> findRankingGeralComNome();
//
//    // Ranking por sala: soma por sala e por usuário
//    @Query("SELECT new com.tcc.drakes.dtos.RankingDTO(r.usuario.nome, SUM(r.pontuacao)) " +
//           "FROM Ranking r WHERE r.sala.idSala = :idSala " +
//           "GROUP BY r.usuario.nome ORDER BY SUM(r.pontuacao) DESC")
//    List<RankingDTO> findRankingPorSala(Long idSala);
//    
//    Optional<Ranking> findByUsuarioAndSala(Usuario usuario, Sala sala);
//
//}
