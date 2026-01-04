package com.example.ChatApp.Infrastructure.Controller;

import com.example.ChatApp.Application.Conversation.DTO.ConversationListDTO;
import com.example.ChatApp.Application.Conversation.Handler.GetConversationListHandler;
import com.example.ChatApp.Application.Conversation.Query.GetConversationListQuery;
import com.example.ChatApp.Application.User.Command.ChangeProfileCommand;
import com.example.ChatApp.Application.User.DTO.UserProfileDTO;
import com.example.ChatApp.Application.User.Handler.ChangeProfileHandler;
import com.example.ChatApp.Application.User.Handler.GetCurrentUserHandler;
import com.example.ChatApp.Application.User.Query.GetCurrentUserQuery;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import com.example.ChatApp.Domain.User.Model.Avatar;
import com.example.ChatApp.Domain.ValueObject.Gender;
import com.example.ChatApp.Infrastructure.Controller.Request.User.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final GetConversationListHandler handler;

    @GetMapping("/get_list")
    public ConversationListDTO getList(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int limit,
            Principal principal
    ) {
        return handler.handle(
                new GetConversationListQuery(
                        UserId.of(principal.getName()),
                        cursor,
                        limit
                )
        );
    }
}