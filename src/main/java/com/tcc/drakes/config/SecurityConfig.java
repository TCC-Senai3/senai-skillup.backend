package com.tcc.drakes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // Importe a SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desabilita a proteção CSRF, comum em APIs stateless (sem sessão no servidor)
                .csrf(csrf -> csrf.disable())
                
                // Configura a política de gerenciamento de sessão para STATELESS
                // Isso diz ao Spring Security para não criar sessões, ideal para APIs com tokens (JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // Configura as regras de autorização para os endpoints HTTP
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público (sem autenticação) aos endpoints de cadastro e login
                        .requestMatchers(HttpMethod.POST, "/usuarios/cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/login").permitAll()
                        
                        // Exige a permissão (Role) de "ADMIN" para acessar a lista de usuários
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")

                        // Exige a permissão (Role) de "USER" para atualizar a biografia
                        // (Lembre-se que o ADMIN também tem a role USER, então ele também pode acessar)
                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}/biografia").hasRole("USER")
                        
                        // Exige que qualquer outra requisição não especificada acima seja autenticada
                        .anyRequest().authenticated()
                )
                .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}