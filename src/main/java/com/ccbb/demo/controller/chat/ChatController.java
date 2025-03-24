package com.ccbb.demo.controller.chat;

import com.ccbb.demo.chat.adapter.in.web.dto.ChatMessageRequest;
import com.ccbb.demo.chat.adapter.in.web.dto.ChatMessageResponse;
import com.ccbb.demo.chat.adapter.out.persistence.ChatMessageJpaEntity;
import com.ccbb.demo.chat.application.port.in.ChatMessageCreateUseCase;
import com.ccbb.demo.chat.application.port.in.command.ChatMessageCreateCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Field;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageCreateUseCase chatMessageCreateUseCase;
    @MessageMapping("/chat/rooms/{roomId}/send")
    @SendTo("/topic/public/rooms/{roomId}")
    public ChatMessageResponse sendMessage(@DestinationVariable("roomId") Long roomId, @Payload ChatMessageRequest chatMessage) {
        ChatMessageCreateCommand chatMessageCreateCommand = ChatMessageCreateCommand.builder()
                .content(chatMessage.text())
                .refreshToken(chatMessage.from())
                .roomId(roomId)
                .build();

        ChatMessageJpaEntity chat = chatMessageCreateUseCase.createChatMessage(chatMessageCreateCommand); // DB에 등록 후 Chat Message Id 반환

        // 필드 동적으로 접근해서 출력
        for (Field field : chat.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true); // private 필드에 접근 허용
                Object value = field.get(chat); // 필드 값 가져오기
                System.out.println(field.getName() + ": " + value);
            } catch (IllegalAccessException e) {
                System.err.println("접근할 수 없는 필드: " + field.getName());
            }
        }

        ChatMessageResponse chatMessageResponse = ChatMessageResponse.builder()
                .id(chat.getId())
                .content(chat.getContent())
                .creatorId(chat.getCreatorId())
                .createdAt(chat.getCreatedAt())
                .creator(chat.getCreator())
                .build();
        return chatMessageResponse;
    }
}