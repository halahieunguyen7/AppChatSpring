package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;

@Embeddable
public class ParticipantId implements Serializable {

    @Getter
    private String conversationId;
    @Getter
    private String userId;

    protected ParticipantId() {}

    public static ParticipantId create(String conversationId, String userId) {
        ParticipantId p = new ParticipantId();
        p.conversationId = conversationId;
        p.userId = userId;
        return p;
    }
}
