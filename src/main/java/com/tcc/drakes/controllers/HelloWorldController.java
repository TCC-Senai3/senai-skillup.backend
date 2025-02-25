package com.tcc.drakes.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {
	
	@GetMapping
	public String helloWordl() {
		return "Hello World";
	}
	
	@GetMapping("/database")
    public  String database() {
        // Defina suas credenciais e a URL de conexão fornecida pelo Aiven
        String url = "jdbc:mysql://tccsenai-osdrakedosenai.f.aivencloud.com:16812/tcc";
        String username = "avnadmin";
        String password = "AVNS_K_Y4nR5Y098mcXNujOn";

        // Tente se conectar ao banco de dados
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Se a conexão for bem-sucedida, exibe a mensagem
            return "Conexao bem sucedida";
        } catch (SQLException e) {
            // Caso haja erro na conexão, exibe a mensagem de erro
            return "Erro de conexão";
        }
    }

}
