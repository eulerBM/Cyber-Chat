package chat.cyber.config;

import chat.cyber.service.response.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<ErroResponse> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {

            ErroResponse erroResponse = new ErroResponse(error.getDefaultMessage(), 400);

            errors.add(erroResponse);

        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }
}
