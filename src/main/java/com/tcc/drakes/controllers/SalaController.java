package com.tcc.drakes.controllers;

import com.tcc.drakes.dtos.SalaDTO;
import com.tcc.drakes.services.SalaService;
import jakarta.persistence.EntityNotFoundException; // <<< Adicionar import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;           // <<< Adicionar import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;     // <<< Garante todos os imports de anotações

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Permite acesso de qualquer origem (ajuste se necessário para produção)
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    // --- Endpoints Existentes ---

    @GetMapping
    public ResponseEntity<List<SalaDTO>> findAll() {
        List<SalaDTO> list = salaService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> findById(@PathVariable Long id) {
        try {
            SalaDTO dto = salaService.findById(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) { // Captura exceção específica
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404
        }
    }

    // Endpoint para buscar sala pelo CÓDIGO (String)
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<SalaDTO> findByCodigo(@PathVariable String codigo) {
         try {
            SalaDTO dto = salaService.findByCodigo(codigo);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) { // Captura exceção específica
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404
        }
    }

    @PostMapping
    public ResponseEntity<SalaDTO> insert(@RequestBody SalaDTO dto) {
        // Adicionar try-catch se o service.insert puder lançar exceções específicas
        try {
            SalaDTO newSala = salaService.insert(dto);
            // Retorna 201 Created com a localização seria o ideal, mas 200 OK é comum
            return ResponseEntity.ok(newSala);
        } catch (RuntimeException e) { // Captura erro genérico (ex: usuário não encontrado)
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Retorna 400
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> update(@PathVariable Long id, @RequestBody SalaDTO dto) {
         try {
            SalaDTO updatedSala = salaService.update(id, dto);
            return ResponseEntity.ok(updatedSala);
        } catch (EntityNotFoundException e) { // Captura exceção específica
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            salaService.delete(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (Exception e) { // Captura erros (ex: sala não encontrada)
             // Pode retornar 404 se a sala não existia
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para FECHAR a sala (apenas dono, via PUT)
    @PutMapping("/{id}/fechar")
    public ResponseEntity<?> fecharSala(@PathVariable Long id) { // Retorna Object ou ? para erro
        try {
            SalaDTO salaFechada = salaService.fecharSala(id);
            return ResponseEntity.ok(salaFechada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) { // Captura "sala já fechada"
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint para ENTRAR na sala (usando CÓDIGO)
    @PostMapping("/{codigoSala}/entrar/{idUsuario}") // Corrigido para corresponder ao SecurityConfig
    public ResponseEntity<String> entrarNaSala(
            @PathVariable String codigoSala,
            @PathVariable Long idUsuario) {
        try {
            String mensagem = salaService.entrarNaSala(codigoSala, idUsuario);
            return ResponseEntity.ok(mensagem);
        } catch (EntityNotFoundException e) { // Sala ou usuário não encontrado
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) { // Sala cheia ou fechada
             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage()); // 403 Forbidden faz sentido aqui
        } catch (Exception e) { // Outros erros
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar entrar na sala.");
        }
    }


    
    @DeleteMapping("/{codigoSala}/sair/{idUsuario}")
    public ResponseEntity<String> sairDaSala(
            @PathVariable String codigoSala,
            @PathVariable Long idUsuario
    ) {
        try {
            // Chama o serviço para remover o participante
            salaService.removerParticipante(codigoSala, idUsuario); // Método criado no Service
            String mensagem = "Usuário " + idUsuario + " saiu da sala " + codigoSala + " com sucesso.";
            return ResponseEntity.ok(mensagem);

        } catch (EntityNotFoundException e) { // Sala ou Usuário não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) { // Ex: Dono tentando sair
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) { // Outros erros inesperados
            System.err.println("Erro inesperado ao sair da sala: " + e.getMessage()); // Log do erro
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao tentar sair da sala.");
        }
    }

}