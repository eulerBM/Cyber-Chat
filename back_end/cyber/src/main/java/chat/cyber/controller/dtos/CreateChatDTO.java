package chat.cyber.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateChatDTO(


        UUID idPublicUserSend,


        UUID idPublicUserReceived

) {
}
