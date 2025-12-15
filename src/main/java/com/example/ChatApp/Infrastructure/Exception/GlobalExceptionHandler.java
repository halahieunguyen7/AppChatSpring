package com.example.ChatApp.Infrastructure.Exception;

import com.example.ChatApp.Domain.Auth.Exception.AuthDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---------------- Bean Validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> new FieldError(
                        err.getField(),
                        err.getRejectedValue() != null ? err.getRejectedValue().toString() : "null",
                        err.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Validation failed", errors));
    }

    // ---------------- Custom Domain Exception
    @ExceptionHandler(AuthDomainException.class)
    public ResponseEntity<ErrorResponse> handleAuthDomainException(AuthDomainException ex) {
        FieldError fieldError = new FieldError(
                null, null, ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Domain error 1", List.of(fieldError)));
    }

    // ---------------- Missing request parameter
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        FieldError fieldError = new FieldError(
                ex.getParameterName(),
                null,
                "Parameter is required"
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Missing request parameter", List.of(fieldError)));
    }

    // ---------------- JSON parse errors
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Malformed JSON request", List.of()));
    }

    // ---------------- ConstraintViolationException (@RequestParam / @PathVariable)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<FieldError> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> new FieldError(
                        v.getPropertyPath().toString(),
                        v.getInvalidValue() != null ? v.getInvalidValue().toString() : "null",
                        v.getMessage()
                ))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Validation failed", errors));
    }

    // ---------------- Fallback cho các exception khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        ex.printStackTrace(); // log ra console
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Internal server error", List.of(
                        new FieldError(null, null, ex.getMessage())
                )));
    }
}
