package com.example.ChatApp.Domain.Conversation.Model;

import com.example.ChatApp.Domain.Conversation.Event.MessageSentEvent;
import com.example.ChatApp.Domain.Conversation.Exception.ChatDomainException;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.MessageContent;
import com.example.ChatApp.Domain.Conversation.ValueObject.Participant;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;

import java.util.*;
import java.util.stream.Collectors;

public class Conversation {

    private final ConversationId id;
    private final Set<Participant> participants;
    private final List<Message> messages;

    // Domain events (optional but rất nên)
    private final List<Object> domainEvents = new ArrayList<>();

    private Conversation(
            ConversationId id,
            Set<Participant> participants,
            List<Message> messages
    ) {
        if (participants == null || participants.size() < 2) {
            throw new ChatDomainException("Conversation must have at least 2 participants");
        }
        this.id = id;
        this.participants = participants;
        this.messages = messages;
    }

    /* ---------- Factory ---------- */

    public static Conversation create(Set<UserId> userIds) {
        Set<Participant> participants = userIds.stream()
                .map(Participant::new)
                .collect(Collectors.toSet());

        return new Conversation(
                ConversationId.newId(),
                participants,
                new ArrayList<>()
        );
    }

    /* ---------- Business Methods ---------- */

    public void sendMessage(UserId senderId, MessageContent content) {
        validateParticipant(senderId);

        Message message = Message.create(senderId, content);
        messages.add(message);

        domainEvents.add(
                new MessageSentEvent(
                        id.value(),
                        senderId.value(),
                        content.value(),
                        message.getSentAt()
                )
        );
    }

    /* ---------- Rules ---------- */

    private void validateParticipant(UserId senderId) {
        boolean exists = participants.stream()
                .anyMatch(p -> p.userId().equals(senderId));

        if (!exists) {
            throw new ChatDomainException("User is not a participant of this conversation");
        }
    }

    /* ---------- Getters ---------- */

    public ConversationId getId() { return id; }
    public Set<Participant> getParticipants() { return Collections.unmodifiableSet(participants); }
    public List<Message> getMessages() { return Collections.unmodifiableList(messages); }

    public List<Object> pullDomainEvents() {
        List<Object> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }
}