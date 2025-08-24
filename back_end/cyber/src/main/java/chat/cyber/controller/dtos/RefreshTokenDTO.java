package chat.cyber.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenDTO(

        @NotBlank(message = "O Refresh Token é obrigatória")
        @Size(min = 10, max = 500, message = "Refresh token teve ter até 500 caracteres")
        String refreshToken
) {
}
