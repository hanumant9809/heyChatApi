package hey.chat.config;

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

        config.enableSimpleBroker("/topic"); //  /topic/messages  => client ko /topic prefix se subscribe krna hoga
        config.setApplicationDestinationPrefixes("/app"); // /app/chat  => client publish message karega on this prefix route

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat") // clinet subscribe this endpoint. ispe connection establish hoga
                .setAllowedOrigins("http://localhost:3000") // Allow requests from this origin
                .withSockJS(); // Enable SockJS fallback options used in front end code

        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
    }
}
