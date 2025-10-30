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
import java.util.Collections;
import java.util.List;

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
				.cors(cors -> cors.configurationSource(corsConfigurationSource())) 
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						
						// Rotas Públicas/WebSocket (Permitidas primeiro)
						.requestMatchers("/ws/**").permitAll() 
						
						// 1. Outras Rotas Públicas
						.requestMatchers(
								"/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/hello-world/**"
						).permitAll()
						.requestMatchers(HttpMethod.POST,
								"/usuarios/cadastro", "/usuarios/login", "/senha/esqueceu", "/senha/reset"
						).permitAll()
						.requestMatchers(HttpMethod.GET, "/ranking/geral").permitAll()

						// 2. Rotas Autenticadas (Qualquer ROLE)
						.requestMatchers(HttpMethod.PUT, "/usuarios/{id}/biografia").authenticated()
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

						// 3. Rotas Protegidas por Autoridade (Roles Específicas)
						.requestMatchers(HttpMethod.POST, "/formularios", "/formularios/completo", "/temas", "/perguntas", "/alternativas")
						.hasAuthority("ROLE_CRIADOR_FORMULARIO")
						.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

						// 4. Qualquer outra requisição
						.anyRequest().authenticated()
				)
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(atividadeUsuarioFilter, JwtAuthenticationFilter.class)
				.build();
	}

	
	// --- CORREÇÃO DE CORS PARA PRODUÇÃO/WEBSOCKET ---
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		// ✅ CORREÇÃO 1: Removida a barra final da URL do Vercel
		configuration.setAllowedOrigins(Arrays.asList(
				"http://localhost:3000", 
				"https://senaiskillup.vercel.app" // Sem a barra final "/"
		));
		
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
		
		// ✅ CORREÇÃO 2: Voltado para "*" para garantir que nenhum header do SockJS seja bloqueado
		configuration.setAllowedHeaders(Arrays.asList("*"));
		
		// Essencial para SockJS / credenciais
		configuration.setAllowCredentials(true); 
		
		// Opcional, mas boa prática
		configuration.setExposedHeaders(Arrays.asList("Authorization"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	// --- FIM DA CORREÇÃO ---

	// AuthenticationProvider (Mantido)
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

	// AuthenticationManager (Mantido)
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}