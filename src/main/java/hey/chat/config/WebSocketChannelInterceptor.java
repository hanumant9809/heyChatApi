package hey.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == null) {
            return message;
        }

        switch (accessor.getCommand()) {
            case CONNECT:
                logger.info("=== WebSocket CONNECT ===");
                logger.info("Client connecting to WebSocket endpoint");
                logger.info("Session ID: {}", accessor.getSessionId());
                logger.info("User: {}", accessor.getUser());
                break;

            case SUBSCRIBE:
                logger.info("=== WebSocket SUBSCRIBE ===");
                String destination = accessor.getDestination();
                logger.info("Session ID: {}", accessor.getSessionId());
                logger.info("Client subscribing to topic: {}", destination);

                // Check if it's a room topic
                if (destination != null && destination.startsWith("/topic/room/")) {
                    String roomId = destination.replace("/topic/room/", "");
                    logger.info("TOPIC CREATED/ACCESSED: /topic/room/{}", roomId);
                    logger.info("Room topic is now active for receiving messages");
                    logger.info("Subscription ID: {}", accessor.getSubscriptionId());
                }
                break;

            case SEND:
                logger.info("=== WebSocket SEND ===");
                logger.info("Session ID: {}", accessor.getSessionId());
                logger.info("Message destination: {}", accessor.getDestination());
                logger.info("Message type: {}", message.getPayload().getClass().getSimpleName());
                break;

            case UNSUBSCRIBE:
                logger.info("=== WebSocket UNSUBSCRIBE ===");
                logger.info("Session ID: {}", accessor.getSessionId());
                logger.info("Subscription ID: {}", accessor.getSubscriptionId());
                if (accessor.getDestination() != null &&
                    accessor.getDestination().startsWith("/topic/room/")) {
                    String roomId = accessor.getDestination().replace("/topic/room/", "");
                    logger.info("Client unsubscribed from topic: /topic/room/{}", roomId);
                }
                break;

            case DISCONNECT:
                logger.info("=== WebSocket DISCONNECT ===");
                logger.info("Session ID: {}", accessor.getSessionId());
                logger.info("Client disconnected from WebSocket");
                break;

            default:
                logger.debug("WebSocket Command: {}", accessor.getCommand());
                break;
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == StompCommand.SEND && sent) {
            logger.info("=== Message Successfully Published ===");
            logger.info("Destination: {}", accessor.getDestination());
            logger.info("Message has been queued for delivery to all subscribers");
        }
    }
}

