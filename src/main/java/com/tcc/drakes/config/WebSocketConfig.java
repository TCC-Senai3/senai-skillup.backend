package com.tcc.drakes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuração do WebSocket (STOMP/SockJS).
 * * Esta classe habilita o suporte ao WebSocket e ao protocolo STOMP,
 * e configura o broker de mensagens e os endpoints.
 */
@Configuration
@EnableWebSocketMessageBroker // Habilita o processamento de mensagens WebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita um broker de mensagens simples na memória para enviar mensagens
        // de volta ao cliente em destinos que começam com "/topic" (broadcast) ou "/queue" (privado)
        config.enableSimpleBroker("/topic", "/queue"); 

        // Define o prefixo para destinos de mensagens VINDAS DO CLIENTE para o servidor.
        // Mensagens enviadas para "/app/..." serão roteadas para os controladores (@MessageMapping).
        config.setApplicationDestinationPrefixes("/app");
        
        // Define o prefixo de destino específico para o usuário (User Destination Prefix)
        // Isso é crucial para que mensagens direcionadas a usuários específicos funcionem corretamente.
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registra o endpoint que os clientes usarão para se conectar ao WebSocket (ex: ws://localhost:8080/ws)
        registry.addEndpoint("/ws")
             
                .setAllowedOrigins(
                    "http://localhost:3000", 
                    "https://tccdrakes.azurewebsites.net",
                    "https://senaiskillup.vercel.app"
                )
              
                .withSockJS();
    }
}