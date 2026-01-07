package com.example.ChatApp.Infrastructure.Elasticsearch;

import com.example.ChatApp.Domain.Conversation.Event.MessageSentEvent;
import com.example.ChatApp.Infrastructure.Mapper.Message.MessageSearchMapper;
import com.example.ChatApp.Infrastructure.Persistence.Message.MessageSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSearchIndexService {

    private final MessageSearchRepository repository;
    private final MessageSearchMapper mapper;


    public void index(MessageSentEvent event) {
        MessageDocument doc = mapper.toDocument(event);
        repository.save(doc);
    }
}

