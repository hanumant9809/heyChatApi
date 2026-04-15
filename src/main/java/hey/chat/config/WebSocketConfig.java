package hey.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Autowired
    private WebSocketChannelInterceptor webSocketChannelInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        logger.info("=== Configuring Message Broker ===");

        logger.info("Enabling Simple Broker for /topic prefix");
        config.enableSimpleBroker("/topic");

        logger.info("Setting Application Destination Prefixes to /app");
        config.setApplicationDestinationPrefixes("/app");

        logger.info("Message Broker Configuration completed successfully");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        logger.info("=== Registering STOMP Endpoints ===");

        logger.info("Registering STOMP endpoint: /chat");
        logger.info("Allowed Origins: http://localhost:3000");

        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();

        logger.info("STOMP endpoint registration completed successfully");

        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        logger.info("Registering WebSocket Channel Interceptor for inbound messages");
        registration.interceptors(webSocketChannelInterceptor);
    }
}
