package chat.cyber.controller.dtos;

import java.util.UUID;

public record CreateChatDTO(

        UUID idPublicUserSend,
        UUID idPublicUserReceived

) {
}
