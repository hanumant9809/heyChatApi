package hey.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO for room response
 * Returns room details with message DTOs (not entities) to avoid circular references
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomId;
    private List<MessageResponse> messages;
    private Integer userCount;
    private List<String> users;
}

