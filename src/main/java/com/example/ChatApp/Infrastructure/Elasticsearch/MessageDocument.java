package com.example.ChatApp.Infrastructure.Elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;

@Document(indexName = "index_messages")
public class MessageDocument {

    @Setter
    @Getter
    @Id
    private String id;
    @Setter
    @Getter
    private String conversationId;
    @Setter
    @Getter
    private String senderId;
    @Setter
    @Getter
    private String message;
    @Setter
    @Getter
    private Instant sentAt;

    // constructor
}
