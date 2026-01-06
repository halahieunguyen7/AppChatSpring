package com.example.ChatApp.Application.Conversation.Handler;

import com.example.ChatApp.Application.Conversation.Command.CreateDirectConversationCommand;
import com.example.ChatApp.Application.Conversation.Command.CreateGroupConversationCommand;
import com.example.ChatApp.Domain.BaseException;
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
public class CreateDirectConversationHandler {

    private final ConversationRepository conversationRepository;

    public ConversationId handle(CreateDirectConversationCommand cmd) {
        if (conversationRepository.existedDirect(UserId.of(cmd.userId1()), UserId.of(cmd.userId2()))) {
            throw new BaseException("Đã tồn tại cuộc trò chuyện");
        }

        Participant user1 = Participant.create(
                UserId.of(cmd.userId1()),
                ParticipantRole.MEMBER
        );

        Participant user2 = Participant.create(
                UserId.of(cmd.userId2()),
                ParticipantRole.MEMBER
        );

        Conversation conversation = Conversation.createDirect(
                ConversationId.newId(),
                user1,
                user2
        );

        conversationRepository.save(conversation);

        return conversation.getId();
    }
}
