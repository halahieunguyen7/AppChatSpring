package com.example.ChatApp.Infrastructure.Redis;

import com.example.ChatApp.Infrastructure.Consumer.WsEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WsEventSubscriber {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    @Value("${instance.id}")
    private String instanceId;

    public void onMessage(Object message) {
        WsEvent event = objectMapper.convertValue(message, WsEvent.class);


        if (!instanceId.equals(event.getTargetInstance())) {
            return;
        }
        log.error("✅ Converted WsEvent target={}", event.getTargetInstance());

        messagingTemplate.convertAndSendToUser(
                event.getUserId(),
                "/queue/chat",
                event.getPayload()
        );
    }
}