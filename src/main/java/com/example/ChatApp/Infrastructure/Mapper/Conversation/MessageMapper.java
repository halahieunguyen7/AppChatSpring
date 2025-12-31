package com.example.ChatApp.Infrastructure.Mapper.Conversation;

import com.example.ChatApp.Domain.Conversation.Model.Message;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.MessageContent;
import com.example.ChatApp.Domain.Conversation.ValueObject.MessageId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import com.example.ChatApp.Infrastructure.Persistence.Conversation.MessageJpaEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MessageMapper {

    private MessageMapper() {}

    public static MessageJpaEntity toEntity(Message m) {
        log.info("messsageMapper toEntity: " +  m.getContent().toString());
        MessageJpaEntity e = MessageJpaEntity.create(
                m.getId().toString(),
                m.getConversationId().toString(),
                m.getSenderId().toString(),
                m.getContent().toString(),
                m.getSentAt()
        );

        return e;
    }

    public static Message toDomain(MessageJpaEntity e) {
        return Message.of(
                MessageId.of(e.getId()),
                UserId.of(e.getSenderId()),
                ConversationId.of(e.getConversationId()),
                new MessageContent(e.getContent()),
                e.getSentAt()
        );
    }
}
