package com.tcc.drakes.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // Broker para envio (broadcast e privado)
        config.enableSimpleBroker("/topic", "/queue");

        // Prefixo de mensagens enviadas DO CLIENTE → SERVIDOR
        config.setApplicationDestinationPrefixes("/app");

        // Prefixo para envio ao usuário específico
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // 🔵 1) ENDPOINT WEBSOCKET NATIVO (necessário para React Native)
        registry.addEndpoint("/ws-native")
            .setAllowedOrigins(
                "http://localhost:3000",
                "https://tccdrakes.azurewebsites.net",
                "https://senaiskillup.vercel.app"
            );

        // 🟠 2) ENDPOINT PARA NAVEGADORES (SockJS)
        registry.addEndpoint("/ws")
            .setAllowedOrigins(
                "http://localhost:3000",
                "https://tccdrakes.azurewebsites.net",
                "https://senaiskillup.vercel.app"
            )
            .withSockJS();
    }
}