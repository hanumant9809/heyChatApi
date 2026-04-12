package hey.chat.controller;


import hey.chat.dto.MessageRequest;
import hey.chat.entity.Message;
import hey.chat.entity.Room;
import hey.chat.repository.MessageRepository;
import hey.chat.repository.RoomRepository;
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

    private final MessageRepository messageRepository;

    private final RoomRepository roomRepository;

    public ChatController(MessageRepository messageRepository, RoomRepository roomRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }
    @PostMapping("/createMessage")
    public ResponseEntity<Message> createMessage(@PathVariable String roomId, Message message) {
        Optional<Room> roomOptional = roomRepository.findByRoomId(roomId);

        if(roomOptional.isPresent()){
            message.setRoom(roomOptional.get());
            messageRepository.save(message);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        return ResponseEntity.notFound().build();
    }
    // for sending and receiving messages

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")  // ispe client subscribe krega
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request) {

        Optional<Room> room = roomRepository.findByRoomId(request.getRoomId());

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimestamp(LocalDateTime.now());

        if(room.isPresent()){
            room.get().getMessages().add(message);
            roomRepository.save(room.get());
        }else {
            throw new RuntimeException("room not found");
        }
        return message;

    }
}
