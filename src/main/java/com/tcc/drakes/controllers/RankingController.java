//package com.tcc.drakes.controllers;
//
//import com.tcc.drakes.dtos.RankingDTO;
//import com.tcc.drakes.services.RankingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/ranking")
//@CrossOrigin(origins = "*") // Libera CORS para todos os domínios
//public class RankingController {
//
//    @Autowired
//    private RankingService rankingService;
//
//    // Adiciona ponto ao usuário que acertou
//    @PostMapping("/ponto")
//    public void adicionarPonto(@RequestParam Long idUsuario, @RequestParam Long idSala) {
//        rankingService.adicionarPonto(idUsuario, idSala);
//    }
//
//    // Retorna ranking por sala
//    @GetMapping("/sala/{idSala}")
//    public List<RankingDTO> getRankingPorSala(@PathVariable Long idSala) {
//        return rankingService.listarRankingPorSala(idSala);
//    }
//
//    // Retorna ranking geral (vitalício)
//    @GetMapping("/geral")
//    public List<RankingDTO> getRankingGeral() {
//        return rankingService.listarRankingGeral();
//    }
//}
