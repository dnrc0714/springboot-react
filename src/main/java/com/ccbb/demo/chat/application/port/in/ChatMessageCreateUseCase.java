package com.ccbb.demo.chat.application.port.in;


import com.ccbb.demo.chat.adapter.out.persistence.ChatMessageJpaEntity;
import com.ccbb.demo.chat.application.port.in.command.ChatMessageCreateCommand;

public interface ChatMessageCreateUseCase {

    ChatMessageJpaEntity createChatMessage(ChatMessageCreateCommand command);
}