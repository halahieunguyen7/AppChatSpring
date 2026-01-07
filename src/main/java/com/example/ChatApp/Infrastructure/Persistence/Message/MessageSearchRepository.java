package com.example.ChatApp.Infrastructure.Persistence.Message;

import com.example.ChatApp.Infrastructure.Elasticsearch.MessageDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MessageSearchRepository
        extends ElasticsearchRepository<MessageDocument, String> {
}
