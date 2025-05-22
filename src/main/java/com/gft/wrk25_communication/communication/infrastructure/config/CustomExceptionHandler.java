package com.gft.wrk25_communication.communication.infrastructure.config;

import com.gft.wrk25_communication.communication.infrastructure.error.ErrorResponse;
import com.gft.wrk25_communication.communication.infrastructure.exception.NotificationNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        return ResponseEntity.internalServerError().body(new ErrorResponse("An error has occurred."));
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationNotFoundException(NotificationNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}
