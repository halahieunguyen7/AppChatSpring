package com.example.ChatApp.Application.Conversation.DTO;

import java.util.List;

public record ConversationListDTO(
        List<ConversationItemDTO> items,
        String nextCursor
) {
}