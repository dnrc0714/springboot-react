package com.ccbb.demo.controller.chat;

import com.ccbb.demo.chat.adapter.in.web.dto.ChatFileRequest;
import com.ccbb.demo.chat.adapter.in.web.dto.ChatMessageRequest;
import com.ccbb.demo.chat.adapter.in.web.dto.ChatMessageResponse;
import com.ccbb.demo.chat.adapter.out.persistence.ChatMessageJpaEntity;
import com.ccbb.demo.chat.application.port.in.ChatFileCreateUseCase;
import com.ccbb.demo.chat.application.port.in.ChatMessageCreateUseCase;
import com.ccbb.demo.chat.application.port.in.command.ChatFileCreateCommand;
import com.ccbb.demo.chat.application.port.in.command.ChatMessageCreateCommand;
import com.ccbb.demo.entity.ChatFileJpaEntity;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.util.JwtUtil;
import com.ccbb.demo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageCreateUseCase chatMessageCreateUseCase;
    private final ChatFileCreateUseCase chatFileCreateUseCase;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/rooms/{roomId}/send")
    @SendTo("/topic/public/rooms/{roomId}")
    public ChatMessageResponse sendMessage(@DestinationVariable("roomId") Long roomId, @Payload ChatMessageRequest chatMessage, Principal principal) {

        ChatMessageCreateCommand chatMessageCreateCommand = ChatMessageCreateCommand.builder()
                .content(chatMessage.content())
                .type(chatMessage.type())
                .principal(principal)
                .roomId(roomId)
                .build();

        ChatMessageJpaEntity chat = chatMessageCreateUseCase.createChatMessage(chatMessageCreateCommand); // DB에 등록 후 Chat Message Id 반환

         ChatMessageResponse chatMessageResponse = ChatMessageResponse.builder()
                .id(chat.getId())
                .content(chat.getContent())
                .creatorId(chat.getCreatorId())
                .createdAt(chat.getCreatedAt())
                .creator(chat.getCreator())
                .build();
        System.out.println("chatMessageResponse --- " + chat);
        return chatMessageResponse;
    }

    // 파일 메시지 처리 (REST 엔드포인트)
    @PostMapping("/files/{roomId}")
    public void uploadFile(@PathVariable("roomId") Long roomId, @ModelAttribute ChatFileRequest chatFileRequest
                        , @RequestHeader(value = "Authorization", required = false) String authHeader) {

        ChatMessageCreateCommand chatMessageCreateCommand = ChatMessageCreateCommand.builder()
                .type(chatFileRequest.type())
                .token(authHeader)
                .roomId(roomId)
                .build();

        ChatMessageJpaEntity chat = chatMessageCreateUseCase.createChatMessage(chatMessageCreateCommand); // DB에 등록 후 Chat Message Id 반환

        ChatFileCreateCommand chatFileCreateCommand = ChatFileCreateCommand.builder()
                .files(chatFileRequest.files())
                .file(chatFileRequest.file())
                .chatRoomId(roomId)
                .chatId(chat.getId())
                .type(chatFileRequest.type())
                .token(authHeader)
                .build();

        List<ChatFileJpaEntity> chatFile = chatFileCreateUseCase.createChatFile(chatFileCreateCommand);

        messagingTemplate.convertAndSend("/topic/public/rooms/"+roomId, chatFile);
    }
}