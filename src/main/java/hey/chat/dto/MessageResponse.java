package hey.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for sending messages via WebSocket
 * Excludes room entity to avoid circular reference and lazy initialization issues
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private String sender;
    private String content;
    private LocalDateTime timestamp;
    private Long roomId;
}

