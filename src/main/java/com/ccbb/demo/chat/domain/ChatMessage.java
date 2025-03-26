package com.ccbb.demo.chat.domain;

import com.ccbb.demo.entity.UserJpaEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ChatMessage{
    private id chatId;
    private String content;
    private Long creatorId;
    private String type;
    private UserJpaEntity creator;
    private ChatRoom.id chatRoomId;
    public record id(Long value) {
    }
}
