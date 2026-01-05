package com.example.ChatApp.Application.Conversation.DTO;

import java.util.List;

public record MessageListDTO(
        List<MessageItemDTO> items,
        String nextCursor
) {
}