package com.example.ChatApp.Infrastructure.Consumer;

import com.example.ChatApp.Domain.Conversation.Event.MessageSentEvent;
import com.example.ChatApp.Infrastructure.Elasticsearch.MessageSearchIndexService;
import io.confluent.connect.avro.AvroConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.connect.data.SchemaAndValue;
import org.apache.kafka.connect.data.Struct;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageEventConsumer {
    private final MessageSearchIndexService messageSearchIndexService;

    @KafkaListener(topics = "chatapp-chatapp.messages")
    public void consume(
            ConsumerRecord<byte[], byte[]> record,
            Acknowledgment ack
    ) {
        try {
            AvroConverter converter = new AvroConverter();

            Map<String, Object> props = new HashMap<>();
            props.put("schema.registry.url", "http://localhost:8085");
            props.put("schemas.enable", true);

            converter.configure(props, false); // false = value

            SchemaAndValue schemaAndValue =
                    converter.toConnectData(record.topic(), record.value());

            Struct payload = (Struct) schemaAndValue.value();
            Struct after = payload.getStruct("after");

            if (after == null) {
                ack.acknowledge();
                return;
            }

            String id = after.getString("id");
            String conversationId = after.getString("conversation_id");
            String senderId = after.getString("sender_id");

            String message = after.getString("content");
            Date sentAtDate = (Date) after.get("sent_at");
            Instant sentAt = sentAtDate.toInstant();

            log.info("conversationId: {}, senderId: {}, message: {}, sentAt: {}", conversationId, senderId, message, sentAt.toString());
            MessageSentEvent e = new MessageSentEvent(
                    id,
                    conversationId,
                    senderId,
                    message,
                    sentAt
            );

            messageSearchIndexService.index(e);

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to process CDC message, skipping", e);
            ack.acknowledge(); // skip poison record
        }
    }
}
