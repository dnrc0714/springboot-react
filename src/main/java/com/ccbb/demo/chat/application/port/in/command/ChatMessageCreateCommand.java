package com.ccbb.demo.chat.application.port.in.command;

import lombok.Builder;
import java.security.Principal;

@Builder
public record ChatMessageCreateCommand(Long roomId, String content, String type, Principal principal, String token) {
}