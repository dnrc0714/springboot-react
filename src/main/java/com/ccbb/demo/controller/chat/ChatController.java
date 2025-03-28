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


@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageCreateUseCase chatMessageCreateUseCase;
    @MessageMapping("/chat/rooms/{roomId}/send")
    @SendTo("/topic/public/rooms/{roomId}")
    public ChatMessageResponse sendMessage(@DestinationVariable("roomId") Long roomId, @Payload ChatMessageRequest chatMessage) {

        ChatMessageCreateCommand chatMessageCreateCommand = ChatMessageCreateCommand.builder()
                .content(chatMessage.content())
                .refreshToken(chatMessage.from())
                .type(chatMessage.type())
                .roomId(roomId)
                .build();

        ChatMessageJpaEntity chat = chatMessageCreateUseCase.createChatMessage(chatMessageCreateCommand); // DB에 등록 후 Chat Message Id 반환


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