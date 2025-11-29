package hey.chat.controller;

import hey.chat.dto.CreateRoomRequest;
import hey.chat.entity.Message;
import hey.chat.entity.Room;
import hey.chat.repository.MessageRepository;
import hey.chat.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public RoomController(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    // create room

    @PostMapping(value = "/create")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest request) {
        String roomId = request != null ? request.getRoomId() : null;

        // check if room already exist or not msg=> room already exists
        if (roomId != null && roomRepository.existsByRoomId(roomId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Room already exists");
        }

        Room room = new Room();
        room.setRoomId(roomId);
        Room saved = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // get room
    @GetMapping("/join/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
        // find room from DB and using roomId and return it.
        Optional<Room> roomOptional = roomRepository.findByRoomId(roomId);
        if(roomOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(roomOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
    }

    // get messages of the room
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId) {
        // Use repository.findAll() to avoid relying on a custom finder that may not be defined.
        List<Message> messages = messageRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
