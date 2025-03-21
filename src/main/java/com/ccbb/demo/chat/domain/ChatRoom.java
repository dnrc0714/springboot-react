package com.ccbb.demo.chat.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ChatRoom {
    private id id;
    private String name;
    private String creator_id;
    private List<ChatMessage> messageList;
    public record id(Long value) {
    }
}