package com.example.ChatApp.Application.General.DTO;

import com.example.ChatApp.Domain.ValueObject.CursorBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CursorBaseCodec {

    private final ObjectMapper objectMapper;

    public String encode(CursorBase cursor) {
        try {
            CursorPayload payload = new CursorPayload(
                    cursor.id()
            );

            byte[] json = objectMapper.writeValueAsBytes(payload);
            return Base64.getUrlEncoder().encodeToString(json);

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cursor encode", e);
        }
    }

    public CursorBase decode(String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return null;
        }

        try {
            byte[] decoded = Base64.getUrlDecoder().decode(cursor);
            CursorPayload payload = objectMapper.readValue(decoded, CursorPayload.class);

            return new CursorBase(
                    payload.id()
            );

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cursor decode", e);
        }
    }

    private record CursorPayload(
            String id
    ) {
    }
}