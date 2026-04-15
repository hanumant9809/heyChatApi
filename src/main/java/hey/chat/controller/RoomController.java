package hey.chat.controller;

import hey.chat.config.Constants;
import hey.chat.dto.CreateRoomRequest;
import hey.chat.dto.JoinRoomRequest;
import hey.chat.dto.MessageResponse;
import hey.chat.dto.RoomResponse;
import hey.chat.entity.Message;
import hey.chat.entity.Room;
import hey.chat.repository.MessageRepository;
import hey.chat.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = "*")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public RoomController(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest request) {
        logger.info("=== Create Room Request ===");
        String roomId = request != null ? request.getRoomId() : null;
        logger.info("RoomId: {}", roomId);

        if (roomId != null && roomRepository.existsByRoomId(roomId)) {
            logger.warn("Room already exists - RoomId: {}", roomId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Constants.MSG_ROOM_ALREADY_EXISTS);
        }

        Room room = new Room();
        room.setRoomId(roomId);
        Room saved = roomRepository.save(room);
        logger.info("Room created successfully - RoomId: {}, Id: {}", roomId, saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinRoom(@RequestBody JoinRoomRequest request) {
        logger.info("=== Join Room Request ===");
        String roomId = request != null ? request.getRoomId() : null;
        String userName = request != null ? request.getUserName() : "Anonymous";
        logger.info("RoomId: {}, UserName: {}", roomId, userName);

        Optional<Room> roomOptional = roomRepository.findByRoomId(roomId);
        if(roomOptional.isEmpty()) {
            logger.warn("Room not found - RoomId: {}", roomId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.MSG_ROOM_NOT_FOUND);
        }

        Room room = roomOptional.get();
        logger.info("Room found - RoomId: {}, RoomId (DB): {}", roomId, room.getId());

        // Convert messages to DTOs to avoid circular reference
        List<MessageResponse> messageResponses = messageRepository
            .findByRoom_RoomId(roomId)
            .stream()
            .map(msg -> {
                MessageResponse dto = new MessageResponse();
                dto.setId(msg.getId());
                dto.setSender(msg.getSender());
                dto.setContent(msg.getContent());
                dto.setTimestamp(msg.getTimestamp());
                dto.setRoomId(room.getId());
                return dto;
            })
            .toList();

        // Build response with room details
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setRoomId(room.getRoomId());
        response.setMessages(messageResponses);
        response.setUserCount(1); // In real scenario, track active WebSocket connections
        response.setUsers(List.of(userName)); // In real scenario, maintain user list per room

        logger.info("User joined room - RoomId: {}, UserName: {}", roomId, userName);
        logger.info("Room response - Total messages: {}, UserCount: {}", response.getMessages().size(), response.getUserCount());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/join/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId) {
        logger.info("=== Get Room Request ===");
        logger.info("RoomId: {}", roomId);

        Optional<Room> roomOptional = roomRepository.findByRoomId(roomId);
        if(roomOptional.isEmpty()) {
            logger.warn("Room not found - RoomId: {}", roomId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.MSG_ROOM_NOT_FOUND);
        }

        logger.info("Room found - RoomId: {}", roomId);
        return ResponseEntity.status(HttpStatus.OK).body(roomOptional.get());
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId) {
        logger.info("=== Get Room Messages Request ===");
        logger.info("RoomId: {}", roomId);

        List<Message> messages = messageRepository.findByRoom_RoomId(roomId);
        logger.info("Messages retrieved - RoomId: {}, Count: {}", roomId, messages.size());

        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
