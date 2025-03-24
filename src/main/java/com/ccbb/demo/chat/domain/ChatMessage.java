package com.ccbb.demo.chat.domain;

import com.ccbb.demo.entity.UserJpaEntity;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ChatMessage{
    private id chatId;
    private String content;
    private Long creatorId;
    private UserJpaEntity creator;
    private ChatRoom.id chatRoomId;
    public record id(Long value) {
    }
}
