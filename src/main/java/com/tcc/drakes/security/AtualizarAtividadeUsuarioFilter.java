package com.tcc.drakes.security;

import com.tcc.drakes.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AtualizarAtividadeUsuarioFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication != null && authentication.isAuthenticated()) {
            String userEmail = authentication.getName();

            usuarioRepository.findByEmail(userEmail).ifPresent(usuario -> {
                usuario.setDataUltimaAtividade(LocalDateTime.now());
                usuarioRepository.save(usuario);
            });
        }

        filterChain.doFilter(request, response);
    }
}