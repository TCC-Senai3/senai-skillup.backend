package com.tcc.drakes.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.services.SalaService;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SalaService salaService;

    // Método chamado quando o DONO envia a mensagem "iniciar"
    @MessageMapping("/sala/{codigoSala}/iniciar")
    public void handleIniciarJogo(
            @DestinationVariable String codigoSala,
            Map<String, Object> payload // Espera receber o ID do usuário no payload
    ) {
        try {
            Long idUsuario = ((Number) payload.get("idUsuario")).longValue(); // Pegando o dono

            System.out.println("Recebida mensagem para iniciar sala: " + codigoSala + " pelo usuário: " + idUsuario);

            Sala salaEntity = salaService.iniciarSala(codigoSala, idUsuario);

            // Cria payload para enviar aos participantes
            Map<String, Object> mensagem = Map.of(
                "type", "JOGO_INICIADO",
                "idFormulario", salaEntity.getFormulario().getIdFormulario(),
                "idSala", salaEntity.getIdSala(),
                "codigoSala", codigoSala,
                "status", salaEntity.getStatusSala().toString()
            );

            String destination = "/topic/sala/" + codigoSala;
            messagingTemplate.convertAndSend(destination, mensagem);

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o jogo: " + e.getMessage());
            messagingTemplate.convertAndSend(
                "/topic/sala/" + codigoSala,
                Map.of("type", "ERRO", "message", e.getMessage())
            );
        }
    }
}
