package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "conversations")
public class ConversationJpaEntity {

    @Id
    @Getter
    private String id;

    @Enumerated(EnumType.STRING)
    @Getter
    private ConversationType type;

    @Getter
    private String title;

    @Setter
    @Getter
    private String lastMessagePreview;
    @Setter
    @Getter
    private Instant lastMessageAt;

    @Getter
    private Instant createdAt;

    @OneToMany(
            mappedBy = "conversation",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Getter
    private Set<ParticipantJpaEntity> participants = new HashSet<>();

    protected ConversationJpaEntity() {}

    public static ConversationJpaEntity create(
            String id,
            ConversationType type,
            String title,
            Instant createdAt
    ) {
        ConversationJpaEntity e = new ConversationJpaEntity();
        e.id = id;
        e.type = type;
        e.title = title;
        e.createdAt = createdAt;
        return e;
    }
}