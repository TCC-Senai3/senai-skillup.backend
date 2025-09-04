package com.tcc.drakes.services;

import java.io.FileOutputStream;
import java.io.IOException;
// Importe as classes Path e Paths para manipular caminhos de forma segura
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.drakes.dtos.RelatorioUsuarioDTO;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.UsuarioRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class RelatorioUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ## MUDANÇA 1: O método agora recebe o DIRETÓRIO e o NOME DO ARQUIVO separadamente.
    // Isso torna o código mais claro, seguro e flexível.
    public void gerarRelatorio(String diretorio, String nomeArquivo) throws JRException, IOException {
        
        List<Usuario> usuarios = usuarioRepository.findAll();

        List<RelatorioUsuarioDTO> usuariosRelatorio = usuarios.stream()
                .map(RelatorioUsuarioDTO::new)
                .collect(Collectors.toList());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(usuariosRelatorio);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", "Relatório de Usuários");

        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/relatorios/relatorio_usuarios.jrxml"));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
        
        // ## MUDANÇA 2: Construir o caminho completo de forma segura
        // Usamos a classe 'Path' para juntar o diretório e o nome do arquivo.
        // Isso funciona bem em qualquer sistema operacional (Windows, Linux, etc.).
        Path caminhoCompleto = Paths.get(diretorio, nomeArquivo);

        // Mensagem de log para ajudar a depurar. Você pode vê-la no console.
        System.out.println("Salvando relatório em: " + caminhoCompleto.toString());

        // ## MUDANÇA 3: Usar o caminho completo para criar o arquivo de saída
        // O 'try-with-resources' garante que o FileOutputStream seja fechado corretamente.
        try (FileOutputStream outputStream = new FileOutputStream(caminhoCompleto.toFile())) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (IOException e) {
            // Lançamos a exceção com uma mensagem mais detalhada
            throw new IOException("Erro ao salvar o relatório de usuários em " + caminhoCompleto.toString(), e);
        }
    }
}