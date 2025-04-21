package com.ccbb.demo.chat.application.port.out;

import com.ccbb.demo.chat.domain.ChatMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoadChatMessagePort {
    List<ChatMessage> loadChatMessegeList(Long roomId, Pageable pageRequest);
}