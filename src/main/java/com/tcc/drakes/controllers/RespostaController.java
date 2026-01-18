package com.tcc.drakes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.dtos.RespostaDTO;
import com.tcc.drakes.services.RespostaService;

@RestController
@RequestMapping("/respostas")
@CrossOrigin("*")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    public ResponseEntity<RespostaDTO> responder(@RequestBody RespostaDTO dto) {
        RespostaDTO resposta = respostaService.salvarResposta(dto);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping
    public ResponseEntity<List<RespostaDTO>> listar() {
        return ResponseEntity.ok(respostaService.listarRespostas());
    }
}
