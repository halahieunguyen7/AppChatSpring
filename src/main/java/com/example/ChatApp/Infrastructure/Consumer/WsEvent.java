package com.example.ChatApp.Infrastructure.Consumer;

import com.example.ChatApp.Application.Conversation.DTO.ChatMessageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsEvent {
    private String targetInstance;
    private String userId;
    private ChatMessageResponse payload;

    public String getTargetInstance()
    {
        return targetInstance;
    }

    public String userId()
    {
        return userId;
    }

    public ChatMessageResponse payload()
    {
        return payload;
    }
}