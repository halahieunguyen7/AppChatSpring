package com.example.ChatApp.Infrastructure.Exception;

import java.util.List;

public class ErrorResponse {
    private String message;
    private List<FieldError> errors;

    public ErrorResponse(String message, List<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    // getters
    public String getMessage() { return message; }
    public List<FieldError> getErrors() { return errors; }
}