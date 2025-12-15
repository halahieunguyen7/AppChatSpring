package com.example.ChatApp.Infrastructure.Exception;

public class FieldError {
    private String field;
    private String rejectedValue;
    private String reason;

    public FieldError(String field, String rejectedValue, String reason) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.reason = reason;
    }

    // getters
    public String getField() { return field; }
    public String getRejectedValue() { return rejectedValue; }
    public String getReason() { return reason; }
}