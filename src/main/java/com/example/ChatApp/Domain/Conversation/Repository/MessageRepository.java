package com.example.ChatApp.Domain.Conversation.Repository;

import com.example.ChatApp.Domain.Conversation.Model.Message;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;

import java.util.List;

public interface MessageRepository {
    void save(Message message);
    List<Message> findByConversation(
            ConversationId id,
            int limit,
            int offset
    );
}
