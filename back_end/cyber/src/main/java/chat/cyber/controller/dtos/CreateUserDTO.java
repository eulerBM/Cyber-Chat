package chat.cyber.controller.dtos;

public record CreateUserDTO(

        String name,
        String email,
        String password,
        String passwordConfirm
) {
}
