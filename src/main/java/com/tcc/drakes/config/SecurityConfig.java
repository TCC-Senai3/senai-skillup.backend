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
@EnableMethodSecurity
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider,
                                                   JwtAuthenticationFilter jwtAuthFilter, AtualizarAtividadeUsuarioFilter atividadeUsuarioFilter)
            throws Exception {

        return http
                // Configuração de CORS (Usa o método corsConfigurationSource definido abaixo)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // Desabilita CSRF (necessário para APIs Stateless)
                .csrf(csrf -> csrf.disable())
                
                // Define a sessão como Stateless (sem estado)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                .authorizeHttpRequests(authorize -> authorize

                        // ✅ 1. CORREÇÃO DE PREFLIGHT E ERROS
                        // Permite requisições OPTIONS (Necessário para o navegador validar o CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Libera o endpoint de erro do Spring para ver as mensagens reais (evita 403 em erros internos)
                        .requestMatchers("/error").permitAll()

                        // ✅ 2. CORREÇÃO DO WEBSOCKET
                        // Adicionado /ws-native/** (conexão do mobile) e /ws/** (genérico)
                        .requestMatchers("/ws/**", "/ws-native/**").permitAll()

                        // 3. Rotas Públicas (Documentação e Utilitários)
                        .requestMatchers(
                                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/hello-world/**"
                        ).permitAll()

                        // 4. Rotas de Autenticação Públicas
                        .requestMatchers(HttpMethod.POST,
                                "/usuarios/cadastro", "/usuarios/login", "/senha/esqueceu", "/senha/reset"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/ranking/geral").permitAll()

                        // 5. Rotas Autenticadas Gerais (Qualquer usuário logado)
                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}/biografia", "usuarios/{id}/avatar").authenticated()
                        .requestMatchers(HttpMethod.GET,
                                "/formularios", "/salas/codigo/{codigo}", "/usuarios/me", "/usuarios/{id}"
                        ).authenticated()
                        .requestMatchers(HttpMethod.POST,
                                "/respostas", "/salas", "/salas/{codigoSala}/entrar/{idUsuario}"
                        ).authenticated()
                        .requestMatchers(HttpMethod.PUT,
                                "/salas/{id}/fechar", "/usuarios/{id}"
                        ).authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/salas/{codigoSala}/sair/{idUsuario}")
                        .authenticated()

                        // 6. Rotas com Permissões Específicas (Roles)
                        .requestMatchers(HttpMethod.POST, "/formularios", "/formularios/completo", "/temas", "/perguntas", "/alternativas")
                        .hasAuthority("ROLE_CRIADOR_FORMULARIO")

                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}/roles").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                        // 7. Bloqueia qualquer outra rota não listada acima
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                // Filtro de JWT antes do filtro padrão de usuário/senha
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Filtro de atividade depois do JWT
                .addFilterAfter(atividadeUsuarioFilter, JwtAuthenticationFilter.class)
                .build();
    }

    // ✅ AQUI ESTÁ A MÁGICA PARA O WEB FUNCIONAR
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ⚠️ MUDANÇA CRUCIAL: Usar setAllowedOriginPatterns("*") ao invés de setAllowedOrigins
        // Isso permite qualquer origem (localhost, vercel, IP de rede) e AINDA permite credenciais.
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permite envio de cookies e headers de autenticação
        configuration.setAllowCredentials(true); 
        
        // Expondo headers importantes para o Frontend ler
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Total-Count"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}