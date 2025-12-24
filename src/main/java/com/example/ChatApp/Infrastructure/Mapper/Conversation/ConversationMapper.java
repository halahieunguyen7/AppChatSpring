package com.example.ChatApp.Infrastructure.Mapper.Conversation;

import com.example.ChatApp.Domain.Conversation.Model.Conversation;
import com.example.ChatApp.Domain.Conversation.Model.MessageSummary;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.Participant;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import com.example.ChatApp.Infrastructure.Persistence.Conversation.ConversationJpaEntity;
import com.example.ChatApp.Infrastructure.Persistence.Conversation.ParticipantId;
import com.example.ChatApp.Infrastructure.Persistence.Conversation.ParticipantJpaEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class ConversationMapper {
    private ConversationMapper() {
    }

    public static Conversation toDomain(ConversationJpaEntity e) {
        Set<Participant> participants =
                e.getParticipants().stream()
                        .map(p -> Participant.of(
                                UserId.of(p.getId().getUserId()),
                                p.getRole(),
                                p.getJoinedAt()
                        ))
                        .collect(Collectors.toSet());

        Conversation conversation = Conversation.of(
                ConversationId.of(e.getId()),
                e.getType(),
                e.getTitle(),
                participants,
                e.getCreatedAt()
        );

        if (e.getLastMessageAt() != null) {
            conversation.updateLastMessage(
                    new MessageSummary(
                            null,
                            null,
                            e.getLastMessagePreview(),
                            e.getLastMessageAt()
                    )
            );
        }

        return conversation;
    }

    public static ConversationJpaEntity toEntity(Conversation c) {
        ConversationJpaEntity e = ConversationJpaEntity.create(
                c.getId().value(),
                c.getType(),
                c.getTitle(),
                c.getCreatedAt()

        );

        if (c.getLastMessage() != null) {
            e.setLastMessagePreview(c.getLastMessage().preview());
            e.setLastMessageAt(c.getLastMessage().sentAt());
        }

        for (Participant p : c.getParticipants()) {
            ParticipantJpaEntity pe = ParticipantJpaEntity.create(
                    ParticipantId.create(
                        c.getId().value(),
                        p.userId().value()
                    ),
                    e,
                    p.getRole(),
                    p.getJoinedAt()
            );

            e.getParticipants().add(pe);
        }

        return e;
    }
}
