package com.ccbb.demo.chat.application.port.in.command;

import com.ccbb.demo.chat.domain.ChatMessage;
import com.ccbb.demo.entity.ChatFileJpaEntity;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record ChatFileCreateCommand (List<MultipartFile> files, MultipartFile file, String type, ChatMessage chatMessage, String token, Long chatRoomId, Long chatId) {
}
