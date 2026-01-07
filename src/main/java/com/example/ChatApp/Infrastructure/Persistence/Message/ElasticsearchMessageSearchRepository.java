package com.example.ChatApp.Infrastructure.Persistence.Message;

import com.example.ChatApp.Infrastructure.Elasticsearch.MessageDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElasticsearchMessageSearchRepository {
    Page<MessageDocument> search(
            String conversationId,
            String content,
            Pageable pageable
    );
}
