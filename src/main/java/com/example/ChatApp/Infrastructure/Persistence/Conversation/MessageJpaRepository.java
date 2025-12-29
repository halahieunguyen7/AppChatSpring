package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface MessageJpaRepository
        extends JpaRepository<MessageJpaEntity, String> {

    @Query("""
        select m from MessageJpaEntity m
        where m.conversationId = :conversationId
        order by m.sentAt desc
    """)
    List<MessageJpaEntity> findByConversation(
            @Param("conversationId") String conversationId,
            Pageable pageable
    );
}
