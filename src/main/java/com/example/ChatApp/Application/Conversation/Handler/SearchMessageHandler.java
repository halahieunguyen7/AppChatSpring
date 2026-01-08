package com.example.ChatApp.Application.Conversation.Handler;

import com.example.ChatApp.Application.Conversation.Query.SearchMessageQuery;
import com.example.ChatApp.Application.General.DTO.CursorBaseCodec;
import com.example.ChatApp.Domain.Conversation.ReadModel.MessageSearchResponse;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationQueryRepository;
import com.example.ChatApp.Domain.Conversation.Repository.MessageQueryRepository;
import com.example.ChatApp.Domain.ValueObject.CursorBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchMessageHandler {
    private final ConversationQueryRepository conversationQueryRepo;
    private final MessageQueryRepository messageQueryRepository;
    private final CursorBaseCodec cursorCodec;

    public MessageSearchResponse handle(SearchMessageQuery query) {
        if (!conversationQueryRepo.userInConversation(
                query.conversationId(),
                query.userId()
        )) {
            return new MessageSearchResponse(
                    List.of(),
                    null
            );
        }

        CursorBase cursor = cursorCodec.decode(query.cursor());

        return messageQueryRepository.searchMessages(
                query.conversationId(),
                query.keyword(),
                cursor != null ? cursor.id() : null,
                10
        );
    }
}