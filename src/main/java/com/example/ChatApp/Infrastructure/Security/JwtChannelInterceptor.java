package com.example.ChatApp.Infrastructure.Security;

import io.jsonwebtoken.Claims;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtProvider;

    public JwtChannelInterceptor(JwtTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(
                        message, StompHeaderAccessor.class
                );

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = (String) accessor
                    .getSessionAttributes()
                    .get("jwt");

            Claims claims = jwtProvider.parseToken(token);
            String userId = claims.getSubject();

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of()
                    );

            accessor.setUser(auth);
        }

        return message;
    }
}
