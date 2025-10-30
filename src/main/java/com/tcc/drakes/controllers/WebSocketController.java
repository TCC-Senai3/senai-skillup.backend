package com.tcc.drakes.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tcc.drakes.dtos.SalaDTO; 
import com.tcc.drakes.entities.Sala; // <<<<<< NOVO: Importa a entidade Sala
import com.tcc.drakes.services.SalaService;

@Controller 
public class WebSocketController { 

    @Autowired
    private SimpMessagingTemplate messagingTemplate; 

    @Autowired
    private SalaService salaService; 

    // Método chamado quando o DONO envia a mensagem "iniciar"
    // O cliente envia para "/app/sala/{codigoSala}/iniciar"
    @MessageMapping("/sala/{codigoSala}/iniciar")
    public void handleIniciarJogo(
            @DestinationVariable String codigoSala 
    ) {
        System.out.println("Recebida mensagem para iniciar sala: " + codigoSala);
        try {
            // 1. CHAMA O NOVO MÉTODO DO SERVICE:
            // Ele busca a Sala, força o carregamento de participantes, 
            // muda o status para INICIADA e garante o contexto @Transactional.
            Sala salaEntity = salaService.iniciarSala(codigoSala);

            // 2. Extrai os IDs da entidade carregada
            Long idFormulario = salaEntity.getFormulario().getIdFormulario(); 
            Long idSala = salaEntity.getIdSala(); 

            if (idFormulario == null || idSala == null) {
                 System.err.println("Erro: idFormulario ou idSala nulos para a sala " + codigoSala);
                 return;
            }

            // 3. Criar o payload da mensagem para enviar aos participantes
            var payload = Map.of(
                "type", "JOGO_INICIADO", 
                "idFormulario", idFormulario,
                "idSala", idSala,
                "codigoSala", codigoSala,
                // Opcional: Enviar status atualizado (INICIADA)
                "status", salaEntity.getStatusSala().toString()
            );

            // 4. Enviar a mensagem para TODOS os inscritos no tópico da sala
            String destination = "/topic/sala/" + codigoSala;
            System.out.println("Enviando JOGO_INICIADO para: " + destination);
            messagingTemplate.convertAndSend(destination, payload);

        } catch (Exception e) {
            System.err.println("Erro ao processar início da sala via WebSocket: " + e.getMessage());
            // Envia uma mensagem de erro de volta ao tópico da sala (opcional, para notificar clientes)
            String errorDestination = "/topic/sala/" + codigoSala;
            messagingTemplate.convertAndSend(errorDestination, Map.of("type", "ERRO", "message", "Não foi possível iniciar o jogo: " + e.getMessage()));
        }
    }

}
