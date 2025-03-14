package com.ccbb.demo.chat.application.port.out;

import com.ccbb.demo.chat.domain.ChatMessage;

public interface CreateChatMessagePort {
    Long createChatMessage(ChatMessage chatMessage);
}