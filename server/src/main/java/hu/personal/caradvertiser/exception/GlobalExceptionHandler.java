package hu.personal.caradvertiser.exception;

import hu.personal.caradvertiser.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorDto errorDto = new ErrorDto()
                .wrongField(ex.getField())
                .message(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({NotValidException.class, OtherUserEntityException.class})
    public ResponseEntity<ErrorDto> handleNotValidAndOtherUserEntityException(NotValidException ex) {
        ErrorDto errorDto = new ErrorDto()
                .wrongField(ex.getField())
                .message(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDto);
    }
}
