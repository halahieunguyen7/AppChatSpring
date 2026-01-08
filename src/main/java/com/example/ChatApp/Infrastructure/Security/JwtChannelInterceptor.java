package com.example.ChatApp.Infrastructure.Security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
            try {
                String authHeader =
                        accessor.getFirstNativeHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return message; // ⚠️ không throw, không return null
                }

                String token = authHeader.substring(7);

                String userId = "";
                Claims claims = jwtProvider.parseToken(token);
                userId = claims.getSubject();
                Authentication auth =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                List.of()
                        );

                accessor.setUser(auth);
                
                StompCommand command = accessor.getCommand();
                log.info(
                        "STOMP command={}, sessionId={}, destination={}, user={}",
                        command,
                        accessor.getSessionId(),
                        accessor.getDestination(),
                        accessor.getUser() != null ? accessor.getUser().getName() : "ANONYMOUS"
                );
            } catch (Exception e) {
                log.error("JWT parse failed", e);
            }
        }

        return message;
    }
}
