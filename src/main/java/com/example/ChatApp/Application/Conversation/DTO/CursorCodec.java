package com.example.ChatApp.Application.Conversation.DTO;

import com.example.ChatApp.Domain.Conversation.ValueObject.Cursor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CursorCodec {

    private final ObjectMapper objectMapper;

    public String encode(Cursor cursor) {
        try {
            CursorPayload payload = new CursorPayload(
                    cursor.lastMessageAt().toString(),
                    cursor.conversationId()
            );

            byte[] json = objectMapper.writeValueAsBytes(payload);
            return Base64.getUrlEncoder().encodeToString(json);

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cursor encode", e);
        }
    }

    public Cursor decode(String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return null;
        }

        try {
            byte[] decoded = Base64.getUrlDecoder().decode(cursor);
            CursorPayload payload = objectMapper.readValue(decoded, CursorPayload.class);

            return new Cursor(
                    Instant.parse(payload.lastMessageAt()),
                    payload.id()
            );

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cursor decode", e);
        }
    }

    private record CursorPayload(
            String lastMessageAt,
            String id
    ) {
    }
}