package com.dj.v_02.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
    }
}
