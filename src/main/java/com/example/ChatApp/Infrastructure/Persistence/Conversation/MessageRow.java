package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import java.time.Instant;

public interface MessageRow {
    String getId();
    String getSenderId();
    String getContent();
    Instant getSentAt();
}