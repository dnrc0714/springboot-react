package com.ccbb.demo.chat.application.port.in.command;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record ChatMessageCreateCommand(Long roomId, String content, String refreshToken, String type) {
}