package com.example.ChatApp.Domain.Conversation.Exception;

public class ChatDomainException extends RuntimeException {
    public ChatDomainException(String message) {
        super(message);
    }

    public ChatDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
