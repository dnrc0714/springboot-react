package com.ccbb.demo.chat.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ChatRoom {
    private RoomId roomId;
    private List<ChatMessage> messageList;
    public record RoomId(Long value) {
    }
}