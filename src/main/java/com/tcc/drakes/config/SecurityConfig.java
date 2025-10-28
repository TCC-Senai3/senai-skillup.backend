package com.tcc.drakes.config;

import com.tcc.drakes.security.AtualizarAtividadeUsuarioFilter;
import com.tcc.drakes.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Mantém, caso use anotações em Services ou Controllers
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AtualizarAtividadeUsuarioFilter atualizarAtividadeUsuarioFilter() {
        return new AtualizarAtividadeUsuarioFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider,
            JwtAuthenticationFilter jwtAuthFilter,
            AtualizarAtividadeUsuarioFilter atividadeUsuarioFilter
    ) throws Exception {

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // CSRF desabilitado, comum para APIs REST com JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API Stateless
                .authorizeHttpRequests(authorize -> authorize
                        // 1. Rotas Públicas (Swagger, Login, Cadastro, Recuperação de Senha)
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/hello-world/**" // Exemplo, pode remover se não usar
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/usuarios/cadastro",
                                "/usuarios/login",
                                "/senha/esqueceu",
                                "/senha/reset"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/relatorios/usuarios", // Ranking geral talvez?
                                "/ranking/geral"       // Ranking geral
                        ).permitAll()
                        // ATENÇÃO: PUT /usuarios/{id}/biografia e /salas** como permitAll parece inseguro. Mude se necessário.
                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}/biografia", "/salas**").permitAll()

                        // 2. Rotas que exigem apenas Autenticação (Qualquer usuário logado)
                        .requestMatchers(HttpMethod.GET, "/formularios").authenticated() // Listar formulários
                        .requestMatchers(HttpMethod.POST, "/respostas").authenticated() // Enviar resposta
                        .requestMatchers(HttpMethod.POST, "/salas").authenticated()    // Criar sala
                        .requestMatchers(HttpMethod.POST, "/salas/{codigoSala}/entrar/{idUsuario}").authenticated() // Entrar na sala
                        .requestMatchers(HttpMethod.GET, "/salas/codigo/{codigo}").authenticated() // Buscar sala por código
                        .requestMatchers(HttpMethod.GET, "/usuarios/me").authenticated() // Buscar perfil próprio

                        // 3. Rotas Protegidas por Autoridade Específica (Roles/Authorities)
                        .requestMatchers(HttpMethod.POST,
                                "/formularios",
                                "/formularios/completo",
                                "/temas",
                                "/perguntas",
                                "/alternativas"
                        ).hasAuthority("ROLE_CRIADOR_FORMULARIO") // <<< Usando ROLE_ prefix consistentemente
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // <<< Usando ROLE_ prefix

                        // 4. Qualquer outra requisição não listada acima deve ser autenticada
                        .anyRequest().authenticated()
                        // --- FIM DAS CORREÇÕES ---
                )
                .authenticationProvider(authenticationProvider)
                // Adiciona os filtros na ordem correta
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(atividadeUsuarioFilter, JwtAuthenticationFilter.class)
                .build();
    }

    // Configuração CORS (parece ok, permite tudo de qualquer origem)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Permite qualquer origem
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permite qualquer header
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a configuração a todas as rotas
        return source;
    }

    // Provedor de Autenticação (usa UserDetailsService e PasswordEncoder)
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Serviço que busca o usuário pelo username
        authProvider.setPasswordEncoder(passwordEncoder);       // Bean que sabe como verificar a senha
        return authProvider;
    }

    // Gerenciador de Autenticação (necessário para o processo de login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}