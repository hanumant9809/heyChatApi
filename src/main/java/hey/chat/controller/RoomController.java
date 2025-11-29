package hey.chat.controller;

import hey.chat.entity.Message;
import hey.chat.entity.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    // create room

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {
        ///  check if room already exist or not msg=> room already exists

        Room room = new Room();
        room.setId(UUID.randomUUID().toString());
        room.setRoomId(roomId);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    // get room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
         // find room from DB and using roomId and return it.
        Room room = new Room();
        room.setId(UUID.randomUUID().toString());
        room.setRoomId(roomId);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    // get messages of the room
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId) {
        // find room using roomId
        // if null return
        // return list of Messages got from that room

        Room room = new Room();
        room.setId(UUID.randomUUID().toString());
        room.setRoomId(roomId);
        room.setMessages(getMessages());

        return ResponseEntity.status(HttpStatus.OK).body(room.getMessages());

    }
    private List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setContent("Hi");
        message.setTimestamp(LocalDateTime.now());

        Message message2 = new Message();
        message2.setContent("Hello World");
        message2.setTimestamp(LocalDateTime.now());

        Message message3 = new Message();
        message3.setContent("Hi 56");
        message3.setTimestamp(LocalDateTime.now());

        messages.add(message);
        return  messages;
    }
}
