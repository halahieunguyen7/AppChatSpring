package com.example.ChatApp.Application.Conversation.Handler;

import com.example.ChatApp.Application.Conversation.DTO.ConversationItemDTO;
import com.example.ChatApp.Application.Conversation.DTO.ConversationListDTO;
import com.example.ChatApp.Application.Conversation.DTO.CursorConversationCodec;
import com.example.ChatApp.Application.Conversation.Query.GetConversationListQuery;
import com.example.ChatApp.Domain.Conversation.ReadModel.ConversationSummary;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationQueryRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.Cursor;
import com.example.ChatApp.Infrastructure.Persistence.ReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@ReadOnly
public class GetConversationListHandler {

    private final ConversationQueryRepository conversationQueryRepository;
    private final CursorConversationCodec cursorCodec;

    public ConversationListDTO handle(GetConversationListQuery query) {

        Cursor cursor = cursorCodec.decode(query.cursor());

        List<ConversationSummary> list =
                conversationQueryRepository.findByUser(
                        query.userId(),
                        cursor,
                        query.limit()
                );

        String nextCursor = list.size() < query.limit()
                ? null
                : cursorCodec.encode(Cursor.from(list.get(list.size() - 1)));

        return new ConversationListDTO(
                list.stream().map(ConversationItemDTO::from).toList(),
                nextCursor
        );
    }
}