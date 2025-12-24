package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import com.example.ChatApp.Domain.Conversation.ValueObject.ParticipantRole;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name = "conversation_participants")
public class ParticipantJpaEntity {

    @Getter
    @EmbeddedId
    private ParticipantId id;

    @Getter
    @MapsId("conversationId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private ConversationJpaEntity conversation;

    @Enumerated(EnumType.STRING)
    @Getter
    private ParticipantRole role;

    @Getter
    private Instant joinedAt;

    protected ParticipantJpaEntity() {}

    public static ParticipantJpaEntity create(
            ParticipantId id,
            ConversationJpaEntity conversation,
            ParticipantRole role,
            Instant joinedAt
    ) {
        ParticipantJpaEntity entity = new ParticipantJpaEntity();
        entity.id = id;
        entity.conversation = conversation;
        entity.role = role;
        entity.joinedAt = joinedAt;

        return entity;
    }
}
