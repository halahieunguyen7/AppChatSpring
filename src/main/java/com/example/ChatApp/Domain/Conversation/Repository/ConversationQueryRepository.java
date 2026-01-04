package com.example.ChatApp.Domain.Conversation.Repository;

import com.example.ChatApp.Domain.Conversation.ReadModel.ConversationSummary;
import com.example.ChatApp.Domain.Conversation.ValueObject.Cursor;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;

import java.util.List;


public interface ConversationQueryRepository {
    List<ConversationSummary> findByUser(
            UserId userId,
            Cursor cursor,
            int limit
    );
}
