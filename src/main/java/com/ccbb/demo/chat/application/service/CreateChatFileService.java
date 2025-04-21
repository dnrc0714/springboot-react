package com.ccbb.demo.chat.application.service;


import com.ccbb.demo.chat.application.port.in.ChatFileCreateUseCase;
import com.ccbb.demo.chat.application.port.in.command.ChatFileCreateCommand;

import com.ccbb.demo.chat.application.port.out.CreateChatFilePort;
import com.ccbb.demo.chat.domain.ChatFiles;
import com.ccbb.demo.common.annotation.UseCase;
import com.ccbb.demo.entity.ChatFileJpaEntity;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
class CreateChatFileService implements ChatFileCreateUseCase {

    private final CreateChatFilePort createChatFilePort;

    @Override
    @Transactional
    public List<ChatFileJpaEntity> createChatFile(ChatFileCreateCommand command) {
        List<ChatFileJpaEntity> chatFilesArr = new ArrayList<>();

        // 1. 여러 개의 파일을 저장 (files가 있을 경우)
        if (command.files() != null) {
            chatFilesArr.addAll(createChatFilePort.createChatFiles(ChatFiles.builder()
                    .id(command.chatId())
                    .files(command.files())
                    .type(command.type())
                    .chatRoomId(command.chatRoomId())
                    .token(command.token())
                    .build()));
        }

        // 2. 단일 파일을 저장 (file이 있을 경우)
        if (command.file() != null) {
            ChatFileJpaEntity singleFile = createChatFilePort.createChatFile(ChatFiles.builder()
                    .id(command.chatId())
                    .file(command.file())
                    .type(command.type())
                    .chatRoomId(command.chatRoomId())
                    .token(command.token())
                    .build());
            chatFilesArr.add(singleFile);
        }

        return chatFilesArr;
    }
}
