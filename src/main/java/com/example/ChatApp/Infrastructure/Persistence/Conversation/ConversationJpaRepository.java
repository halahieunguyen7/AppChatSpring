package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import org.springframework.data.jpa.repository.JpaRepository;

interface ConversationJpaRepository
        extends JpaRepository<ConversationJpaEntity, String> {
}
