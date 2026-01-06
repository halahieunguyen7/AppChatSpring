package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

interface ConversationJpaRepository
        extends JpaRepository<ConversationJpaEntity, String> {

    @Query("""
                SELECT
                    c.id AS id,
                    c.type AS type,
                    c.title AS title,
                    c.avatar AS avatar,
                    c.lastMessageAt AS lastMessageAt,
                    c.lastMessagePreview AS lastMessagePreview,
                    c.createdAt AS createdAt
                FROM ConversationJpaEntity c
                JOIN c.participants p
                WHERE p.id.userId = :userId
                  AND (
                        :cursorLastMessageAt IS NULL
                        OR c.lastMessageAt < :cursorLastMessageAt
                        OR (
                            c.lastMessageAt = :cursorLastMessageAt
                            AND c.id < :cursorConversationId
                        )
                  )
                ORDER BY c.lastMessageAt DESC, c.id DESC
            """)
    List<ConversationRow> findConversations(
            @Param("userId") String userId,
            @Param("cursorLastMessageAt") Instant cursorLastMessageAt,
            @Param("cursorConversationId") String cursorConversationId,
            Pageable pageable
    );

    @Query(value = """
                SELECT
                    cp.user_id AS userId,
                    cp.role AS role,
                    u.full_name AS fullName,
                    u.avatar AS avatar
                FROM conversation_participants cp
                JOIN users u
                ON cp.user_id = u.id
                WHERE cp.conversation_id = :conversationId
            """, nativeQuery = true)
    List<MemberRow> getMemberOfConversations(
            @Param("conversationId") String conversationId
    );

    @Query(value = """
                SELECT EXISTS (
                    SELECT 1
                    FROM conversations c
                    JOIN conversation_participants p
                        ON p.conversation_id = c.id
                    WHERE c.type = 'DIRECT'
                      AND p.user_id IN (:userId1, :userId2)
                    GROUP BY c.id
                    HAVING COUNT(*) = 2
                       AND COUNT(DISTINCT p.user_id) = 2
                )
            """, nativeQuery = true)
    long findExistingDirectConversation(
            @Param("userId1") String userId1,
            @Param("userId2") String userId2
    );

    @Query(value = """
                SELECT EXISTS (
                    SELECT 1
                    FROM conversation_participants cp
                    WHERE cp.user_id = :userId
                      AND cp.conversation_id = :conversationId
                )
            """, nativeQuery = true)
    long userInConversation(
            @Param("conversationId") String conversationId,
            @Param("userId") String userId
    );
}
