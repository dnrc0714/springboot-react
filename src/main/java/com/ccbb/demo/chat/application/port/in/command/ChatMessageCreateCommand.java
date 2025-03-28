package com.ccbb.demo.chat.application.port.in.command;

import com.ccbb.demo.chat.domain.ChatContent;
import lombok.Builder;

@Builder
public record ChatMessageCreateCommand(Long roomId, ChatContent content, String refreshToken, String type) {
}