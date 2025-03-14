package com.ccbb.demo.chat.application.port.in;


import com.ccbb.demo.chat.application.port.in.command.ChatMessageCreateCommand;

public interface ChatMessageCreateUseCase {
    Long createChatMessage(ChatMessageCreateCommand command);
}