package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import java.time.Instant;

public interface ConversationRow {

    String getId();
    String getType();
    String getTitle();
    String getAvatar();
    Instant getLastMessageAt();
    String getLastMessagePreview();
    Instant getCreatedAt();
}