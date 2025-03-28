package com.ccbb.demo.chat.application.service;

import com.ccbb.demo.chat.adapter.out.persistence.ChatMessageJpaEntity;
import com.ccbb.demo.chat.application.port.in.ChatMessageCreateUseCase;

import com.ccbb.demo.chat.application.port.in.command.ChatMessageCreateCommand;
import com.ccbb.demo.chat.application.port.out.CreateChatMessagePort;
import com.ccbb.demo.chat.domain.ChatMessage;
import com.ccbb.demo.chat.domain.ChatRoom;
import com.ccbb.demo.common.annotation.UseCase;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
class CreateChatMessageService implements ChatMessageCreateUseCase {
    private final CreateChatMessagePort createChatMessagePort;

    @Override
    public ChatMessageJpaEntity createChatMessage(ChatMessageCreateCommand command) {

        UserJpaEntity user = SecurityUtil.getCurrentUser();

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(new ChatRoom.id(command.roomId()))
                .content(command.content().getText())
                .files(command.content().getFiles())
                .creatorId(user.getUserId())
                .type(command.type())
                .creator(user)
                .build();

        return createChatMessagePort.createChatMessage(chatMessage);
    }
}