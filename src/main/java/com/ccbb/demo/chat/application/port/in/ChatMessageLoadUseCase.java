package com.ccbb.demo.chat.application.port.in;

import com.ccbb.demo.chat.adapter.in.web.dto.ChatMessageResponse;
import com.ccbb.demo.chat.application.port.in.query.ChatMessageListQuery;

import java.util.List;

public interface ChatMessageLoadUseCase {
    List<ChatMessageResponse> getChatMessageList(ChatMessageListQuery command);
}