package com.example.ChatApp.Domain.Auth.Exception;

public class AuthDomainException extends RuntimeException {

    public AuthDomainException(String message) {
        super(message);
    }

    public AuthDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}