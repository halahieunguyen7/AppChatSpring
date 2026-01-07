package com.example.ChatApp.Infrastructure.Command;

import jakarta.annotation.PostConstruct;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchHealthCheck {

    private final ElasticsearchOperations operations;

    public ElasticsearchHealthCheck(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @PostConstruct
    public void check() {
        boolean exists = operations.indexOps(IndexCoordinates.of("index_messages")).exists();
        System.out.println("Elasticsearch connected: " + exists);
    }
}