package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import com.example.ChatApp.Domain.Conversation.Model.Message;
import com.example.ChatApp.Domain.Conversation.Repository.MessageRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Infrastructure.Mapper.Conversation.MessageMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class MessageRepositoryImpl
        implements MessageRepository {

    private final MessageJpaRepository jpaRepository;

    public MessageRepositoryImpl(MessageJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Message message) {
        jpaRepository.save(MessageMapper.toEntity(message));
    }

    @Override
    public List<Message> findByConversation(
            ConversationId id,
            int limit,
            int offset
    ) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return jpaRepository
                .findByConversation(id.value(), pageable)
                .stream()
                .map(MessageMapper::toDomain)
                .toList();
    }
}
