package com.tcc.drakes.controllers;

import java.io.IOException;
import java.nio.file.InvalidPathException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<String> gerarRelatorioPDF(

			@RequestParam(required = false) String nomeArquivo) {

		try {

			String diretorio = "C:\\Users\\SEDUC DEST1\\Documents\\relatorios";

			if (nomeArquivo == null || nomeArquivo.isBlank()) {

				nomeArquivo = "relatorio_usuarios_" + System.currentTimeMillis() + ".pdf";
			} else {

				if (!nomeArquivo.toLowerCase().endsWith(".pdf")) {
					nomeArquivo += ".pdf";
				}

				if (nomeArquivo.contains("/") || nomeArquivo.contains("\\")) {
					return ResponseEntity.badRequest().body("Nome de arquivo inválido. Não use barras.");
				}
			}

			relatorioUsuarioService.gerarRelatorio(diretorio, nomeArquivo);

			String caminhoCompleto = diretorio + "\\" + nomeArquivo;
			return ResponseEntity.ok("Relatório gerado com sucesso em: " + caminhoCompleto);

		} catch (IOException | JRException | InvalidPathException e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Falha ao gerar o relatório: " + e.getMessage());
		}
	}
}