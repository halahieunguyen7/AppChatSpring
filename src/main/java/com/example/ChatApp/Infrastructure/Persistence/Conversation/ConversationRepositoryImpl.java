package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import com.example.ChatApp.Domain.Conversation.Model.Conversation;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Infrastructure.Mapper.Conversation.ConversationMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class ConversationRepositoryImpl
        implements ConversationRepository {

    private final ConversationJpaRepository jpaRepository;

    public ConversationRepositoryImpl(ConversationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Conversation> findById(ConversationId id) {
        return jpaRepository.findById(id.value())
                .map(ConversationMapper::toDomain);
    }

    @Override
    public void save(Conversation conversation) {
        ConversationJpaEntity entity =
                ConversationMapper.toEntity(conversation);
        jpaRepository.save(entity);
    }
}
