package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import com.example.ChatApp.Domain.Conversation.ReadModel.ConversationSummary;
import com.example.ChatApp.Domain.Conversation.ReadModel.MessageSummary;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationQueryRepository;
import com.example.ChatApp.Domain.Conversation.Repository.MessageQueryRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.Cursor;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import com.example.ChatApp.Domain.ValueObject.CursorBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageQueryRepositoryImpl
        implements MessageQueryRepository {

    private final MessageJpaRepository jpaRepository;

    @Override
    public List<MessageSummary> findByConversation(
            ConversationId conversationId,
            CursorBase cursor,
            int limit
    ) {

        String cursorLastId =
                cursor != null ? cursor.id() : null;

        Pageable pageable = PageRequest.of(0, limit);

        return jpaRepository.findByConversationCursor(
                        conversationId.value(),
                        cursorLastId,
                        pageable
                )
                .stream()
                .map(this::toSummary)
                .toList();
    }

    private MessageSummary toSummary(MessageRow row) {
        return new MessageSummary(
                row.getId(),
                row.getSenderId(),
                row.getContent(),
                row.getSentAt()
        );
    }
}
