//package com.tcc.drakes.services;
//
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.tcc.drakes.dtos.RankingDTO;
//import com.tcc.drakes.entities.Ranking;
//import com.tcc.drakes.entities.Sala;
//import com.tcc.drakes.entities.Usuario;
//import com.tcc.drakes.repositories.RankingRepository;
//import com.tcc.drakes.repositories.SalaRepository;
//import com.tcc.drakes.repositories.UsuarioRepository;
//
//@Service
//public class RankingService {
//
//    @Autowired
//    private RankingRepository rankingRepository;
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @Autowired
//    private SalaRepository salaRepository;
//
//    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//
//    // ✅ Adiciona ponto ao ranking do usuário (por sala e geral)
//    public void adicionarPonto(Long idUsuario, Long idSala) {
//        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
//        Optional<Sala> salaOpt = salaRepository.findById(idSala);
//
//        if (usuarioOpt.isEmpty() || salaOpt.isEmpty()) {
//            throw new IllegalArgumentException("Usuário ou sala não encontrado.");
//        }
//
//        Usuario usuario = usuarioOpt.get();
//        Sala sala = salaOpt.get();
//
//        // Busca ranking por sala
//        Ranking ranking = rankingRepository.findByUsuarioAndSala(usuario, sala)
//                .orElse(new Ranking());
//
//        ranking.setUsuario(usuario);
//        ranking.setSala(sala);
//        ranking.setPontuacao((ranking.getPontuacao() == null ? 0 : ranking.getPontuacao()) + 1);
//
//        rankingRepository.save(ranking);
//    }
//
//    // ✅ Ranking geral (vitalício)
//    public List<RankingDTO> listarRankingGeral() {
//        return rankingRepository.findRankingGeralComNome();
//    }
//
//    // ✅ Ranking por sala
//    public List<RankingDTO> listarRankingPorSala(Long idSala) {
//        return rankingRepository.findRankingPorSala(idSala);
//    }
//}
