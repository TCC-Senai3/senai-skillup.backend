package com.tcc.drakes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.dtos.RankingDTO;
import com.tcc.drakes.services.RankingService;

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

}
