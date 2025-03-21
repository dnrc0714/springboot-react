package com.ccbb.demo.controller.chat;

import com.ccbb.demo.chat.application.port.in.ChatMessageLoadUseCase;
import com.ccbb.demo.chat.application.port.in.query.ChatMessageListQuery;
import com.ccbb.demo.common.annotation.response.SuccessApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatMessageController {
    private final ChatMessageLoadUseCase chatMessageLoadUseCase;

    @GetMapping("/{roomId}/messages")
    public SuccessApiResponse<?> getMessageList(@PathVariable Long roomId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size
    ){
        ChatMessageListQuery query = ChatMessageListQuery.builder()
                .roomId(roomId)
                .page(page)
                .size(size)
                .build();
        return SuccessApiResponse.of(chatMessageLoadUseCase.getChatMessageList(query));
    }
}
