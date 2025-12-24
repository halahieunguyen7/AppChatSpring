package com.example.ChatApp.Domain.Conversation.ValueObject;

import com.example.ChatApp.Domain.Conversation.Exception.ChatDomainException;
import lombok.Getter;

import java.time.Instant;

public class Participant {
    @Getter
    private final UserId userId;
    @Getter
    private final ParticipantRole role;
    @Getter
    private final Instant joinedAt;

    private Participant(UserId userId, ParticipantRole role, Instant joinedAt) {
        this.userId = userId;
        this.role = role;
        this.joinedAt = Instant.now();
    }

    public static Participant create(UserId userId, ParticipantRole role) {
        return new Participant(userId, role, Instant.now());
    }

    public static Participant of(UserId userId, ParticipantRole role, Instant joinedAt) {
        return new Participant(userId, role, joinedAt);
    }

    public UserId userId() {
        return userId;
    }

    public boolean isAdmin() {
        return role == ParticipantRole.ADMIN;
    }
}