package marcelocaldasdevops.com.passin.config;

import marcelocaldasdevops.com.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import marcelocaldasdevops.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import marcelocaldasdevops.com.passin.domain.event.exceptions.EventFullException;
import marcelocaldasdevops.com.passin.domain.event.exceptions.EventNotFoundException;
import marcelocaldasdevops.com.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.AttributeNotFoundException;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFullNotFound(EventFullException exception){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }
    @ExceptionHandler(AttributeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttributeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity handleAttendeeAlreadyExist(AttendeeAlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleCheckInAlreadyExist(CheckInAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
