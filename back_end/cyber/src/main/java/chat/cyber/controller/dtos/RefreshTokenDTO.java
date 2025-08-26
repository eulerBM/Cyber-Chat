package chat.cyber.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RefreshTokenDTO(

        @NotBlank(message = "O Refresh Token é obrigatória")
        @Size(min = 10, max = 500, message = "Refresh token teve ter até 500 caracteres")
        String refreshToken,

        @NotBlank(message = "O UUID é obrigatória")
        @Size(min = 5, max = 100, message = "UUID teve ter até 100 caracteres")
        UUID idPublicUser
) {
}
