package com.ccbb.demo.chat.application.port.out;

import com.ccbb.demo.chat.adapter.out.persistence.ChatMessageJpaEntity;
import com.ccbb.demo.chat.domain.ChatMessage;

public interface CreateChatMessagePort {
    ChatMessageJpaEntity createChatMessage(ChatMessage chatMessage);
}