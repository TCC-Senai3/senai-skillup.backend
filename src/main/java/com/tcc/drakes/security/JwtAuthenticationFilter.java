package com.tcc.drakes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

       
      //  System.out.println(">>> [FILTRO JWT] Etapa 1: Filtro iniciado para a rota: " + request.getRequestURI());
      

        String path = request.getRequestURI();
        if ("/usuarios/cadastro".equals(path) || "/usuarios/login".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
       
      //  System.out.println(">>> [FILTRO JWT] Etapa 2: Token extraído do cabeçalho.");
        

        userEmail = jwtUtil.extractEmail(jwt);
      
       // System.out.println(">>> [FILTRO JWT] Etapa 3: E-mail extraído do token: " + userEmail);
        

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
           
           // System.out.println(">>> [FILTRO JWT] Etapa 4: UserDetails carregado para o usuário: " + userDetails.getUsername());
            

            if (jwtUtil.validateToken(jwt)) {
              
               // System.out.println(">>> [FILTRO JWT] Etapa 5: Token validado com sucesso. Autenticando usuário.");
               
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
              
                 //System.out.println(">>> [FILTRO JWT] ERRO: A validação do token falhou!");
                 
            }
        }
        filterChain.doFilter(request, response);
    }
}