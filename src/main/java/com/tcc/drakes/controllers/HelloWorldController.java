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
	public String database() {

		String url = "jdbc:mysql://tccsenai-osdrakedosenai.f.aivencloud.com:16812/tcc";
		String username = "avnadmin";
		String password = "AVNS_K_Y4nR5Y098mcXNujOn";

		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			return "Conexao bem sucedida";
		} catch (SQLException e) {

			return "Erro de conexão";
		}
	}

}
