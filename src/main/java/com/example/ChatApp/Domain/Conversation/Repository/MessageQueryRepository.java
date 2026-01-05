package com.example.ChatApp.Domain.Conversation.Repository;

import com.example.ChatApp.Domain.Conversation.ReadModel.ConversationSummary;
import com.example.ChatApp.Domain.Conversation.ReadModel.MessageSummary;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.ValueObject.CursorBase;

import java.util.List;

public interface MessageQueryRepository {
    List<MessageSummary> findByConversation(
            ConversationId conversationId,
            CursorBase cursor,
            int limit
    );
}
