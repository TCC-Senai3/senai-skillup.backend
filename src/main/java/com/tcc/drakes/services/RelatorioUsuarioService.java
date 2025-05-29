package com.tcc.drakes.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public void gerarRelatorio(String caminho) throws JRException, IOException {
        // Recupera todos os usuários do banco de dados
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Converte os usuários para o DTO de relatório
        List<RelatorioUsuarioDTO> usuariosRelatorio = usuarios.stream()
                .map(RelatorioUsuarioDTO::new)
                .collect(Collectors.toList());

        // Cria a fonte de dados para o JasperReports
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(usuariosRelatorio);

        // Cria os parâmetros do relatório
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", "Relatório de Usuários");

        // Carrega o template do relatório
        JasperReport jasperReport = JasperCompileManager
                .compileReport(getClass().getResourceAsStream("/relatorios/relatorio_usuarios.jrxml"));

        // Preenche o relatório com os dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // Cria o arquivo PDF de saída
        try (FileOutputStream outputStream = new FileOutputStream(new File(caminho))) {
            // Exporta o relatório para o formato PDF
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (IOException e) {
            throw new IOException("Erro ao salvar o relatório de usuários", e);
        }
    }
}
