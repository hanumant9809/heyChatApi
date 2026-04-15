package hey.chat.controller;

import hey.chat.config.Constants;
import hey.chat.dto.MessageRequest;
import hey.chat.dto.MessageResponse;
import hey.chat.entity.Message;
import hey.chat.entity.Room;
import hey.chat.repository.MessageRepository;
import hey.chat.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;

    public ChatController(MessageRepository messageRepository, RoomRepository roomRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }

    @PostMapping("/createMessage")
    public ResponseEntity<Message> createMessage(@PathVariable String roomId, Message message) {
        logger.info("=== Creating Message via REST Endpoint ===");
        logger.info("RoomId from path variable: {}", roomId);

        Optional<Room> roomOptional = roomRepository.findByRoomId(roomId);

        if(roomOptional.isPresent()){
            logger.info("Room found with ID: {}", roomId);
            message.setRoom(roomOptional.get());
            Message savedMessage = messageRepository.save(message);
            logger.info("Message saved to database - MessageId: {}, Sender: {}, RoomId: {}",
                savedMessage.getId(), savedMessage.getSender(), roomId);
            return ResponseEntity.status(HttpStatus.OK).body(savedMessage);
        }

        logger.warn("Room not found for RoomId: {}", roomId);
        return ResponseEntity.notFound().build();
    }

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public MessageResponse sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request) {

        logger.info("=== WebSocket Message Received ===");
        logger.info("Destination: /app/sendMessage/{}", roomId);
        logger.info("Topic for broadcast: /topic/room/{}", roomId);
        logger.info("Message Request - Sender: {}, Content: {}, RoomId: {}",
            request.getSender(), request.getContent(), request.getRoomId());

        Optional<Room> room = roomRepository.findByRoomId(request.getRoomId());

        if(room.isEmpty()) {
            logger.error("Room not found in database - RoomId: {}", request.getRoomId());
            throw new RuntimeException(Constants.MSG_ROOM_NOT_FOUND);
        }

        logger.info("Room found in database - RoomId: {}", request.getRoomId());

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimestamp(LocalDateTime.now());
        message.setRoom(room.get());

        logger.info("Associating message with room - RoomId: {}", request.getRoomId());

        try {
            Message savedMessage = messageRepository.save(message);
            logger.info("Message saved to database - MessageId: {}, Sender: {}, Content: {}, Timestamp: {}",
                savedMessage.getId(), savedMessage.getSender(), savedMessage.getContent(), savedMessage.getTimestamp());

            logger.info("=== Message Publishing to Topic ===");
            logger.info("Publishing to topic: /topic/room/{}", request.getRoomId());
            logger.info("Message being broadcast - Sender: {}, Content: {}", request.getSender(), request.getContent());
            logger.info("Message will be delivered to all subscribers of /topic/room/{}", request.getRoomId());

            // Convert to DTO to avoid lazy initialization exception during serialization
            MessageResponse response = new MessageResponse();
            response.setId(savedMessage.getId());
            response.setSender(savedMessage.getSender());
            response.setContent(savedMessage.getContent());
            response.setTimestamp(savedMessage.getTimestamp());
            response.setRoomId(room.get().getId());

            return response;
        } catch (Exception e) {
            logger.error("Failed to save message - RoomId: {}, Error: {}", request.getRoomId(), e.getMessage());
            throw new RuntimeException(Constants.MSG_MESSAGE_SAVE_FAILED, e);
        }
    }
}
