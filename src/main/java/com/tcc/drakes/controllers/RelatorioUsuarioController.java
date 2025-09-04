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
@RequestMapping("/relatorios/usuarios") // Mantive a rota base
public class RelatorioUsuarioController {

    @Autowired
    private RelatorioUsuarioService relatorioUsuarioService;

    // O endpoint continua sendo o GET na raiz da rota
    @GetMapping
    public ResponseEntity<String> gerarRelatorioPDF(
            // ## MUDANÇA 1: O parâmetro agora é opcional e espera apenas o NOME do arquivo
            @RequestParam(required = false) String nomeArquivo) {

        try {
            // ## MUDANÇA 2: O diretório de salvamento é definido AQUI, no servidor.
            // Isso é muito mais seguro!
            String diretorio = "C:\\Users\\SEDUC DEST1\\Documents\\relatorios";

            // ## MUDANÇA 3: Lógica para criar um nome padrão se nenhum for enviado
            if (nomeArquivo == null || nomeArquivo.isBlank()) {
                // Cria um nome único usando a data e hora atual para evitar sobrescrever arquivos
                nomeArquivo = "relatorio_usuarios_" + System.currentTimeMillis() + ".pdf";
            } else {
                // Garante que o arquivo termine com .pdf
                if (!nomeArquivo.toLowerCase().endsWith(".pdf")) {
                    nomeArquivo += ".pdf";
                }
                // Validação de segurança simples para evitar ataques de Path Traversal
                if (nomeArquivo.contains("/") || nomeArquivo.contains("\\")) {
                    return ResponseEntity.badRequest().body("Nome de arquivo inválido. Não use barras.");
                }
            }

            // ## MUDANÇA 4: Chamada correta para o serviço, com os dois parâmetros
            relatorioUsuarioService.gerarRelatorio(diretorio, nomeArquivo);

            String caminhoCompleto = diretorio + "\\" + nomeArquivo;
            return ResponseEntity.ok("Relatório gerado com sucesso em: " + caminhoCompleto);

        } catch (IOException | JRException | InvalidPathException e) {
            // ## MUDANÇA 5: Tratamento de erro robusto
            // Se algo der errado no serviço, capturamos o erro e retornamos uma resposta clara.
            e.printStackTrace(); // Imprime o erro no console para você poder depurar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Falha ao gerar o relatório: " + e.getMessage());
        }
    }
}