package com.tcc.drakes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcc.drakes.dtos.RankingDTO;
import com.tcc.drakes.entities.Ranking;
import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.RankingRepository;
import com.tcc.drakes.repositories.SalaRepository;
import com.tcc.drakes.repositories.UsuarioRepository;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SalaRepository salaRepository;

    public List<RankingDTO> listarRankingGeral() {
        List<Object[]> resultados = rankingRepository.getRankingGeral();
        return resultados.stream()
            .map(obj -> new RankingDTO(
                (String) obj[0], // Nome
                (Long) obj[1],   // Pontuação
                (String) obj[2]  //
            ))
            .collect(Collectors.toList());
    }

    public List<RankingDTO> listarRankingPorSala(Long salaId) {
        List<Object[]> resultados = rankingRepository.getRankingPorSala(salaId);
        return resultados.stream()
            .map(obj -> new RankingDTO(
                (String) obj[0], // Nome
                (Long) obj[1],   // Pontuação
                (String) obj[2]  
            ))
            .collect(Collectors.toList());
    }

    @Transactional 
    public void adicionarPontuacao(Long idUsuario, Long idSala, Long pontosParaAdicionar) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        Ranking ranking = rankingRepository.findByUsuarioAndSala(usuario, sala);
        if (ranking == null) {
            ranking = new Ranking(usuario, sala);
        }

        ranking.adicionarPontos(pontosParaAdicionar);
        usuario.adicionarPontos(pontosParaAdicionar);
        
        rankingRepository.save(ranking);
        usuarioRepository.save(usuario); 
    }
}