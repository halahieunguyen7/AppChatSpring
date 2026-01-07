package com.example.ChatApp.Infrastructure.Mapper.Message;

import com.example.ChatApp.Domain.Conversation.Event.MessageSentEvent;
import com.example.ChatApp.Infrastructure.Elasticsearch.MessageDocument;
import org.springframework.stereotype.Component;

@Component
public class MessageSearchMapper {

    public MessageDocument toDocument(MessageSentEvent e) {
        MessageDocument doc = new MessageDocument();
        doc.setId(e.id());
        doc.setConversationId(e.conversationId());
        doc.setSenderId(e.senderId());
        doc.setMessage(e.content());
        doc.setSentAt(e.sentAt());
        return doc;
    }
}
