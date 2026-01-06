package com.example.ChatApp.Application.Conversation.Handler;

import com.example.ChatApp.Application.Conversation.DTO.MessageItemDTO;
import com.example.ChatApp.Application.Conversation.DTO.MessageListDTO;
import com.example.ChatApp.Application.Conversation.Query.GetMemberOfConversationQuery;
import com.example.ChatApp.Application.General.DTO.CursorBaseCodec;
import com.example.ChatApp.Domain.Conversation.ReadModel.MemberSummary;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationQueryRepository;
import com.example.ChatApp.Domain.ValueObject.CursorBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetMemberListOfConversationHandler {

    private final ConversationQueryRepository conversationQueryRepo;
    private final CursorBaseCodec cursorCodec;

    public List<MemberSummary> handle(GetMemberOfConversationQuery query) {
        if (!conversationQueryRepo.userInConversation(
                query.conversationId(),
                query.userId()
        )) {
            return List.of();
        }

        return conversationQueryRepo.getMemberOfConversations(
                query.conversationId()
        );
    }
}