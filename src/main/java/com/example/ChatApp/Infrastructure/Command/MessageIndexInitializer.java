package com.example.ChatApp.Infrastructure.Command;

import com.example.ChatApp.Infrastructure.Elasticsearch.MessageDocument;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageIndexInitializer {

    private final ElasticsearchOperations operations;

    public MessageIndexInitializer(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @PostConstruct
    public void init() {
        try {
            IndexOperations indexOps =
                    operations.indexOps(IndexCoordinates.of("index_messages"));

            if (!indexOps.exists()) {
                indexOps.create();
                indexOps.putMapping(indexOps.createMapping());
            }

            log.info("Elasticsearch index [index_messages] ready");

        } catch (Exception e) {
            log.error("Elasticsearch not available, skipping index init", e);
            // QUAN TRỌNG: không throw lại
        }
    }
}
