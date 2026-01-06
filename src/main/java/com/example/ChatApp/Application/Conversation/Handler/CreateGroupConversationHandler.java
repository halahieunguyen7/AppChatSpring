package com.example.ChatApp.Application.Conversation.Handler;

import com.example.ChatApp.Application.Conversation.Command.CreateGroupConversationCommand;
import com.example.ChatApp.Domain.Conversation.Model.Conversation;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.Participant;
import com.example.ChatApp.Domain.Conversation.ValueObject.ParticipantRole;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CreateGroupConversationHandler {

    private final ConversationRepository conversationRepository;

    public ConversationId handle(CreateGroupConversationCommand cmd) {

        Set<Participant> members = cmd.userIds()
                .stream()
                .map(id -> Participant.create(
                        UserId.of(id),
                        ParticipantRole.MEMBER
                ))
                .collect(Collectors.toSet());
        Participant admin = Participant.create(
                UserId.of(cmd.creatorUserId()),
                ParticipantRole.ADMIN
        );

        members.add(admin);
        for (Participant p : members) {
            log.info(p.getUserId().toString());
        }
        Conversation conversation = Conversation.createNewGroup(
                ConversationId.newId(),
                cmd.title(),
                admin,
                members
        );

        conversationRepository.save(conversation);

        return conversation.getId();
    }
}
