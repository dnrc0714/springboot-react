package com.ccbb.demo.chat.application.service;

import com.ccbb.demo.chat.adapter.out.persistence.ChatMessageJpaEntity;
import com.ccbb.demo.chat.application.port.in.ChatMessageCreateUseCase;

import com.ccbb.demo.chat.application.port.in.command.ChatMessageCreateCommand;
import com.ccbb.demo.chat.application.port.out.CreateChatMessagePort;
import com.ccbb.demo.chat.domain.ChatMessage;
import com.ccbb.demo.chat.domain.ChatRoom;
import com.ccbb.demo.common.annotation.UseCase;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.service.auth.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
class CreateChatMessageService implements ChatMessageCreateUseCase {
    private final CreateChatMessagePort createChatMessagePort;

    private final AuthService authService;

    @Override
    public ChatMessageJpaEntity createChatMessage(ChatMessageCreateCommand command) {

        UserJpaEntity user = authService.jwtTokenToUser(command.refreshToken());

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(new ChatRoom.id(command.roomId()))
                .content(command.content())
                .creatorId(user.getUserId())
                .creator(user)
                .build();

        return createChatMessagePort.createChatMessage(chatMessage);
    }
}