package com.example.ChatApp.Infrastructure.Controller;

import com.example.ChatApp.Application.Conversation.Command.CreateDirectConversationCommand;
import com.example.ChatApp.Application.Conversation.Command.CreateGroupConversationCommand;
import com.example.ChatApp.Application.Conversation.DTO.ConversationListDTO;
import com.example.ChatApp.Application.Conversation.DTO.MessageListDTO;
import com.example.ChatApp.Application.Conversation.Handler.*;
import com.example.ChatApp.Application.Conversation.Query.GetConversationListQuery;
import com.example.ChatApp.Application.Conversation.Query.GetMemberOfConversationQuery;
import com.example.ChatApp.Application.Conversation.Query.GetMessageListQuery;
import com.example.ChatApp.Domain.Conversation.ReadModel.MemberSummary;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import com.example.ChatApp.Infrastructure.Controller.Request.Conversation.CreateDirectConversationRequest;
import com.example.ChatApp.Infrastructure.Controller.Request.Conversation.CreateGroupConversationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final GetConversationListHandler getConversationListHandler;
    private final GetMessageListHandler getMessageListHandler;
    private final GetMemberListOfConversationHandler getMemberListOfConversationHandler;
    private final CreateGroupConversationHandler createGroupConversationHandler;
    private final CreateDirectConversationHandler createDirectConversationHandler;

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

    @PostMapping("/create_group_conversation")
    public Map<String, String> createGroupConversation(
            @Valid @RequestBody CreateGroupConversationRequest req,
            Principal principal
    ) {
        log.info(req.userIds().toString());
        ConversationId id = createGroupConversationHandler.handle(
                new CreateGroupConversationCommand(
                        req.title(),
                        req.userIds(),
                        principal.getName()
                )
        );

        return Map.of("conversationId", id.value());
    }

    @PostMapping("/create_direct_conversation")
    public Map<String, String> createDirectConversation(
            @Valid @RequestBody CreateDirectConversationRequest req,
            Principal principal
    ) {
        ConversationId id = createDirectConversationHandler.handle(
                new CreateDirectConversationCommand(
                        req.userId(),
                        principal.getName()
                )
        );

        return Map.of("conversationId", id.value());
    }

    @GetMapping("/get_member_in_conversation")
    public List<MemberSummary> createDirectConversation(
            @RequestParam String conversationId,
            Principal principal
    ) {
        return getMemberListOfConversationHandler.handle(
                new GetMemberOfConversationQuery(
                        principal.getName(),
                        conversationId
                )
        );
    }
}