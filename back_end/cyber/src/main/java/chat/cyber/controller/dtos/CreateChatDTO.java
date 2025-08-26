package chat.cyber.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateChatDTO(

        @NotBlank(message = "O UUID do send é obrigatória")
        @Size(min = 5, max = 100, message = "UUID teve ter até 100 caracteres")
        UUID idPublicUserSend,

        @NotBlank(message = "O UUID do received é obrigatória")
        @Size(min = 5, max = 100, message = "UUID teve ter até 100 caracteres")
        UUID idPublicUserReceived

) {
}
