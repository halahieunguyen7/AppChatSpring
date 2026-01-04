package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import com.example.ChatApp.Domain.Conversation.ReadModel.ConversationSummary;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationQueryRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationType;
import com.example.ChatApp.Domain.Conversation.ValueObject.Cursor;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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
public class ConversationQueryRepositoryImpl
        implements ConversationQueryRepository {

    private final ConversationJpaRepository jpaRepository;

    @Override
    public List<ConversationSummary> findByUser(
            UserId userId,
            Cursor cursor,
            int limit
    ) {

        Instant cursorLastMessageAt =
                cursor != null ? cursor.lastMessageAt() : null;

        String cursorConversationId =
                cursor != null ? cursor.conversationId() : null;
        log.info(cursorConversationId);
        Pageable pageable = PageRequest.of(0, limit);

        return jpaRepository.findConversations(
                        userId.value(),
                        cursorLastMessageAt,
                        cursorConversationId,
                        pageable
                )
                .stream()
                .map(this::toSummary)
                .toList();
    }

    private ConversationSummary toSummary(ConversationRow row) {
        return new ConversationSummary(
                row.getId(),
                row.getType(),
                row.getTitle(),
                row.getAvatar(),
                row.getLastMessageAt(),
                row.getLastMessagePreview(),
                row.getCreatedAt()
        );
    }
}
