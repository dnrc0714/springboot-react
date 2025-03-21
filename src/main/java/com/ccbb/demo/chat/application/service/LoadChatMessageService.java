package com.ccbb.demo.chat.application.service;

import com.ccbb.demo.chat.adapter.in.web.dto.ChatMessageResponse;
import com.ccbb.demo.chat.application.port.in.ChatMessageLoadUseCase;
import com.ccbb.demo.chat.application.port.in.query.ChatMessageListQuery;
import com.ccbb.demo.chat.application.port.out.LoadChatMessagePort;
import com.ccbb.demo.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class LoadChatMessageService implements ChatMessageLoadUseCase {
    private final LoadChatMessagePort loadChatMessagePort;
    @Override
    public List<ChatMessageResponse> getChatMessageList(ChatMessageListQuery query) {
        PageRequest pageRequest = PageRequest.of(query.page(), query.size(), Sort.by("id").descending());

        return loadChatMessagePort.loadChatMessegeList(query.roomId(), pageRequest)
                .stream().map((chatMessage)->
                        ChatMessageResponse.builder()
                                .id(chatMessage.getChatId().value())
                                .content(chatMessage.getContent())
                                .creatorNickName(chatMessage.getCreatorNickName())
                                .build())
                .collect(Collectors.toList());
    }
}