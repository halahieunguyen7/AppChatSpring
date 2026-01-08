package com.example.ChatApp.Infrastructure.Elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "indexes_messages")
public class MessageDocument {

    @Field(type = FieldType.Keyword)
    @Setter
    @Getter
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    @Setter
    @Getter
    private String conversationId;

    @Field(type = FieldType.Keyword)
    @Setter
    @Getter
    private String senderId;

    @Field(type = FieldType.Text)
    @Setter
    @Getter
    private String message;

    @Field(type = FieldType.Long)
    @Setter
    @Getter
    private Long sentAt;

    // constructor
}
