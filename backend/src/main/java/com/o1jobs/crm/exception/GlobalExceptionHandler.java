package com.o1jobs.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchIntermediaryException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchIntermediaryException(NoSuchIntermediaryException ex) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage(),
                Instant.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchUserException(NoSuchUserException ex) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage(),
                Instant.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoSuchAssignmentException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchAssignmentException(NoSuchAssignmentException ex) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage(),
                Instant.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoSuchCareRecipientException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchCareRecipientException(NoSuchCareRecipientException ex) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage(),
                Instant.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoSuchCaregiverException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchCaregiverException(NoSuchCaregiverException ex) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage(),
                Instant.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoSuchClientException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchClientException(NoSuchClientException ex) {
        ErrorResponse error = new ErrorResponse(
                404,
                ex.getMessage(),
                Instant.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                409,
                ex.getMessage(),
                Instant.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse(
                400,
                "Validation failed",
                Instant.now(),
                ex.getFieldErrors().stream()
                        .collect(
                                Collectors.toMap(
                                        FieldError::getField,
                                        fieldError -> fieldError.getDefaultMessage() != null
                                                ? fieldError.getDefaultMessage() : "Invalid value"
                                )
                        )
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}