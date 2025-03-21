package com.ccbb.demo.chat.domain;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ChatMessage{
    private id chatId;
    private String content;
    private Long creatorId;
    private String creatorNickName;
    private ChatRoom.id chatRoomId;
    public record id(Long value) {
    }
}
