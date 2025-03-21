package com.ccbb.demo.chat.application.service;

import com.ccbb.demo.chat.application.port.in.ChatMessageCreateUseCase;

import com.ccbb.demo.chat.application.port.in.command.ChatMessageCreateCommand;
import com.ccbb.demo.chat.application.port.out.CreateChatMessagePort;
import com.ccbb.demo.chat.domain.ChatMessage;
import com.ccbb.demo.chat.domain.ChatRoom;
import com.ccbb.demo.common.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
class CreateChatMessageService implements ChatMessageCreateUseCase {
    private final CreateChatMessagePort createChatMessagePort;

    @Override
    public Long createChatMessage(ChatMessageCreateCommand command) {
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(new ChatRoom.id(command.roomId()))
                .content(command.content())
                .creatorId(command.creatorId())
                .build();
        return createChatMessagePort.createChatMessage(chatMessage);
    }
}