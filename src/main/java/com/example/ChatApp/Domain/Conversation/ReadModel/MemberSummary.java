package com.example.ChatApp.Domain.Conversation.ReadModel;

import com.example.ChatApp.Domain.Conversation.ValueObject.ParticipantRole;

public record MemberSummary(
        String userId,
        ParticipantRole role,
        String fullName,
        String avatar
) {
}
