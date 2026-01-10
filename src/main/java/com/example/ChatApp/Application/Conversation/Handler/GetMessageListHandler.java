package com.example.ChatApp.Application.Conversation.Handler;

import com.example.ChatApp.Application.Conversation.DTO.ConversationItemDTO;
import com.example.ChatApp.Application.Conversation.DTO.ConversationListDTO;
import com.example.ChatApp.Application.Conversation.DTO.MessageItemDTO;
import com.example.ChatApp.Application.Conversation.DTO.MessageListDTO;
import com.example.ChatApp.Application.Conversation.Query.GetMessageListQuery;
import com.example.ChatApp.Application.General.DTO.CursorBaseCodec;
import com.example.ChatApp.Domain.Conversation.ReadModel.ConversationSummary;
import com.example.ChatApp.Domain.Conversation.ReadModel.MessageSummary;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationQueryRepository;
import com.example.ChatApp.Domain.Conversation.Repository.MessageQueryRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.Cursor;
import com.example.ChatApp.Domain.ValueObject.CursorBase;
import com.example.ChatApp.Infrastructure.Persistence.ReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@ReadOnly
public class GetMessageListHandler {

    private final MessageQueryRepository messageQueryRepository;
    private final CursorBaseCodec cursorCodec;

    public MessageListDTO handle(GetMessageListQuery query) {

        CursorBase cursor = cursorCodec.decode(query.cursor());

        List<MessageSummary> list =
                messageQueryRepository.findByConversation(
                        query.conversationId(),
                        cursor,
                        query.limit()
                );

        String nextCursor = list.size() < query.limit()
                ? null
                : cursorCodec.encode(CursorBase.from(list.get(list.size() - 1).id()));

        return new MessageListDTO(
                list.stream().map(MessageItemDTO::from).toList(),
                nextCursor
        );
    }
}