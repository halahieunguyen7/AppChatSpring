package com.example.ChatApp.Domain.Conversation.Repository;

import com.example.ChatApp.Domain.Conversation.Model.Conversation;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;

import java.util.Optional;
import java.util.Set;

public interface  ConversationRepository {
    Optional<Conversation> findById(ConversationId id);

    void save(Conversation conversation);

    boolean existsByParticipants(Set<UserId> userIds);
}
