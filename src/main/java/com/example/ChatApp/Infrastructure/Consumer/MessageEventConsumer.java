package com.example.ChatApp.Infrastructure.Consumer;

import com.example.ChatApp.Domain.Conversation.Event.MessageSentEvent;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageEventConsumer {


    @KafkaListener(topics = "chatapp-chatapp.messages")
    public void consume(
            JsonNode payload,
            Acknowledgment ack
    ) {
        if (!payload.has("after") || payload.get("after").isNull()) {
            ack.acknowledge();
            return;
        }

        JsonNode value = payload
                .get("after")
                .elements()
                .next(); // chatapp.chatapp.messages.Value

        MessageSentEvent event = new MessageSentEvent(
                value.get("conversation_id").asText(),
                value.get("sender_id").asText(),
                value.get("content").get("string").asText(),
                Instant.parse(value.get("sent_at").asText())
        );

//        indexer.index(event);
        ack.acknowledge();
    }

}
