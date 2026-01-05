package com.example.ChatApp.Infrastructure.Controller;

import com.example.ChatApp.Application.Conversation.DTO.ConversationListDTO;
import com.example.ChatApp.Application.Conversation.DTO.MessageListDTO;
import com.example.ChatApp.Application.Conversation.Handler.GetConversationListHandler;
import com.example.ChatApp.Application.Conversation.Handler.GetMessageListHandler;
import com.example.ChatApp.Application.Conversation.Query.GetConversationListQuery;
import com.example.ChatApp.Application.Conversation.Query.GetMessageListQuery;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final GetConversationListHandler getConversationListHandler;
    private final GetMessageListHandler getMessageListHandler;

    @GetMapping("/get_list")
    public ConversationListDTO getListConversation(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int limit,
            Principal principal
    ) {
        return getConversationListHandler.handle(
                new GetConversationListQuery(
                        UserId.of(principal.getName()),
                        cursor,
                        limit
                )
        );
    }

    @GetMapping("/get_list_message")
    public MessageListDTO getListMessage(
            @RequestParam String conversationId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int limit,
            Principal principal
    ) {
        return getMessageListHandler.handle(
                new GetMessageListQuery(
                        UserId.of(principal.getName()),
                        ConversationId.of(conversationId),
                        cursor,
                        limit
                )
        );
    }
}