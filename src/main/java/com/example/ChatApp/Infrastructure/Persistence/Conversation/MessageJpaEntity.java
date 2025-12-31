package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name = "messages")
public class MessageJpaEntity {

    @Id
    @Getter
    private String id;

    @Getter
    @Column(name = "conversation_id")
    private String conversationId;

    @Getter
    @Column(name = "sender_id")
    private String senderId;

    @Getter
    @Column(columnDefinition = "TEXT")
    private String content;

    @Getter
    @Column(name = "sent_at")
    private Instant sentAt;

    protected MessageJpaEntity() {}

    public static MessageJpaEntity create(
            String id,
            String conversationId,
            String senderId,
            String content,
            Instant sentAt
    ) {
        MessageJpaEntity e = new MessageJpaEntity();
        e.id = id;
        e.conversationId = conversationId;
        e.senderId = senderId;
        e.content = content;
        e.sentAt = sentAt;
        return e;
    }
}
