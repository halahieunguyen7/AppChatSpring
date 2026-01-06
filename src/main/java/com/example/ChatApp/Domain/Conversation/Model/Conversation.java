package com.example.ChatApp.Domain.Conversation.Model;

import com.example.ChatApp.Domain.Conversation.Exception.ChatDomainException;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationType;
import com.example.ChatApp.Domain.Conversation.ValueObject.Participant;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class Conversation {
    @Getter
    private final ConversationId id;
    @Getter
    private final ConversationType type;
    @Getter
    private final Set<Participant> participants;
    @Getter
    private String title; // only for GROUP
    @Getter
    private MessageSummary lastMessage;
    @Getter
    private final Instant createdAt;

    private Conversation(
            ConversationId id,
            ConversationType type,
            Set<Participant> participants,
            String title,
            Instant createdAt
    ) {
        this.id = id;
        this.type = type;
        this.participants = participants;
        this.title = title;
        this.createdAt = createdAt;
    }

    /* ========== Factory ========== */

    public static Conversation createDirect(
            ConversationId id,
            Participant user1,
            Participant user2
    ) {
        return new Conversation(
                id,
                ConversationType.DIRECT,
                Set.of(user1, user2),
                null,
                Instant.now()
        );
    }

    public static Conversation of(
            ConversationId id,
            ConversationType type,
            String title,
            Set<Participant> members,
            Instant createdAt
    ) {
        return new Conversation(
                id,
                type,
                members,
                title,
                createdAt
        );
    }

    public static Conversation createNewGroup(
            ConversationId id,
            String title,
            Participant owner,
            Set<Participant> members
    ) {
        if (!members.contains(owner)) {
            throw new ChatDomainException("Owner must be participant");
        }

        return new Conversation(
                id,
                ConversationType.GROUP,
                members,
                title,
                Instant.now()
        );
    }

    /* ========== Domain behaviors ========== */

    public void validateSender(UserId senderId) {
        if (!isParticipant(senderId)) {
            throw new ChatDomainException("User is not a participant");
        }
    }

    public void updateLastMessage(MessageSummary summary) {
        this.lastMessage = summary;
    }

    public void renameGroup(String newTitle, UserId requester) {
        Participant p = getParticipant(requester);

        if (!p.isAdmin()) {
            throw new ChatDomainException("Only admin can rename group");
        }

        this.title = newTitle;
    }

    /* ========== Helpers ========== */

    private boolean isParticipant(UserId userId) {
        return participants.stream()
                .anyMatch(p -> p.userId().equals(userId));
    }

    private Participant getParticipant(UserId userId) {
        return participants.stream()
                .filter(p -> p.userId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ChatDomainException("Not a participant"));
    }
}