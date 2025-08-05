package com.tcc.drakes.controllers;

import com.tcc.drakes.dtos.RankingDTO;
import com.tcc.drakes.services.RankingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@CrossOrigin("*")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/geral")
    public List<RankingDTO> getRankingGeral() {
        return rankingService.listarRankingGeral();
    }

    @GetMapping("/sala/{idSala}")
    public List<RankingDTO> getRankingPorSala(@PathVariable Long idSala) {
        return rankingService.listarRankingPorSala(idSala);
    }

    @PostMapping("/pontuar")
    public void pontuar(@RequestParam Long idUsuario, @RequestParam Long idSala) {
        rankingService.adicionarPontuacao(idUsuario, idSala);
    }
}
