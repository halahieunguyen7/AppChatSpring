package com.example.ChatApp.Infrastructure.Controller;

import com.example.ChatApp.Application.Conversation.Command.SendMessageCommand;
import com.example.ChatApp.Application.Conversation.Handler.SendMessageHandler;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import com.example.ChatApp.Infrastructure.Controller.Request.Conversation.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatWsController {
    private final SendMessageHandler sendMessageHandler;

    @MessageMapping("/send")
    public void send(ChatMessageRequest req, Principal principal) {
        log.info("principal = {}", principal);
        sendMessageHandler.handle(
                new SendMessageCommand(
                        ConversationId.of(req.conversationId()),
                        UserId.of(principal.getName()),
                        req.content()
                )
        );
    }
}
