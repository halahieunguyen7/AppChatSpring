package com.example.ChatApp.Domain.Auth.Exception;

import com.example.ChatApp.Domain.BaseException;

public class AuthDomainException extends BaseException {

    public AuthDomainException(String message) {
        super(message);
    }

    public AuthDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}