package com.tcc.drakes.controllers; // Ou seu pacote de controller

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.services.PasswordResetService;

@RestController
@RequestMapping("/senha")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

   
    @PostMapping("/esqueceu")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            passwordResetService.criarTokenDeRedefinicao(email);
            return ResponseEntity.ok("Um link para redefinição de senha foi enviado para o seu e-mail.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Se um usuário com este e-mail existir, um link de redefinição será enviado.");
        }
    }

   
    @GetMapping("/reset")
    public ResponseEntity<String> validateResetToken(@RequestParam String token) {
        String validationResult = passwordResetService.validarToken(token);
        if (validationResult != null) {
            
            return ResponseEntity.badRequest().body(validationResult);
        }
        return ResponseEntity.ok("Token válido.");
    }

    
    @PostMapping("/reset")
    public ResponseEntity<String> handlePasswordReset(@RequestParam String token, @RequestParam String newPassword) {
        // Valida o token uma última vez antes de trocar a senha
        String validationResult = passwordResetService.validarToken(token);
        if (validationResult != null) {
            return ResponseEntity.badRequest().body(validationResult);
        }

        // Remove espaços em branco acidentais no início e no fim da senha
        String senhaTratada = newPassword.trim();
        if (senhaTratada.length() < 8) { // Exemplo de validação de tamanho mínimo
            return ResponseEntity.badRequest().body("A senha deve ter no mínimo 8 caracteres.");
        }

        try {
            passwordResetService.redefinirSenha(token, senhaTratada);
            return ResponseEntity.ok("Sua senha foi redefinida com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}