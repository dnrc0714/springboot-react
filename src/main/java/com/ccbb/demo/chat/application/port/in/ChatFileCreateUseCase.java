package com.ccbb.demo.chat.application.port.in;

import com.ccbb.demo.chat.application.port.in.command.ChatFileCreateCommand;
import com.ccbb.demo.entity.ChatFileJpaEntity;

import java.util.List;

public interface ChatFileCreateUseCase {
    List<ChatFileJpaEntity> createChatFile(ChatFileCreateCommand command);
}
