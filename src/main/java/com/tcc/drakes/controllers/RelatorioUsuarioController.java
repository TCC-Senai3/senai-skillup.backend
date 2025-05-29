package com.tcc.drakes.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.services.RelatorioUsuarioService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/relatorios/usuarios")
public class RelatorioUsuarioController {

    @Autowired
    private RelatorioUsuarioService relatorioUsuarioService;

    @GetMapping
    public ResponseEntity<String> gerarRelatorioPDF(@RequestParam String caminho) throws JRException, IOException {
        relatorioUsuarioService.gerarRelatorio(caminho);
        return ResponseEntity.ok("Relatório de usuários gerado com sucesso em: " + caminho);
    }
}
