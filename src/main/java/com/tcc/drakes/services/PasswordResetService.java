package com.tcc.drakes.services; // Ou seu pacote de serviço

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcc.drakes.entities.PasswordResetToken;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.PasswordResetTokenRepository;
import com.tcc.drakes.repositories.UsuarioRepository;

@Service
public class PasswordResetService {

    @Autowired
    private UsuarioRepository usuarioRepository; 

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    private final int EXPIRATION_MINUTES = 60;

    
    public void criarTokenDeRedefinicao(String email) {
        
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));

       
        String tokenValue = UUID.randomUUID().toString();

       
        PasswordResetToken meuToken = new PasswordResetToken();
        meuToken.setUsuario(usuario);
        meuToken.setToken(tokenValue);
        meuToken.setExpiryDate(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));

        
        tokenRepository.save(meuToken);

        
        String resetLink = "https://senaiskillup.vercel.app/ResetPassword?token=" + tokenValue;

     
        String assunto = "Instruções para Redefinição de Senha";
        String texto = "Olá,\n\nVocê solicitou a redefinição da sua senha. " +
                       "Clique no link abaixo para alterá-la. O link é válido por 1 hora.\n\n" +
                       resetLink +
                       "\n\nSe você não fez esta solicitação, por favor, ignore este e-mail.";

        emailService.enviarEmail(usuario.getEmail(), assunto, texto);
    }

   
    public String validarToken(String token) {
        PasswordResetToken passToken = tokenRepository.findByToken(token).orElse(null);

        if (passToken == null) {
            return "Token inválido.";
        }
        if (passToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Token expirado.";
        }

        return null;
    }

   
    public void redefinirSenha(String token, String novaSenha) {
      
        if (validarToken(token) != null) {
            throw new RuntimeException("Token inválido ou expirado.");
        }

        PasswordResetToken passToken = tokenRepository.findByToken(token).get();
        Usuario usuario = passToken.getUsuario();

        
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        
        tokenRepository.delete(passToken);
    }
}