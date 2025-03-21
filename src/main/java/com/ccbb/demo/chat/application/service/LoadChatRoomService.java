package com.ccbb.demo.chat.application.service;

import com.ccbb.demo.chat.domain.ChatRoom;
import com.ccbb.demo.chat.adapter.in.web.dto.ChatRoomItemResponse;
import com.ccbb.demo.chat.adapter.in.web.dto.ChatRoomListReadResponse;
import com.ccbb.demo.chat.adapter.in.web.dto.ChatRoomResponse;
import com.ccbb.demo.chat.application.port.in.ChatRoomLoadUseCase;
import com.ccbb.demo.chat.application.port.in.query.ChatRoomListQuery;
import com.ccbb.demo.chat.application.port.in.query.ChatRoomQuery;
import com.ccbb.demo.chat.application.port.out.LoadChatRoomPort;
import com.ccbb.demo.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class LoadChatRoomService implements ChatRoomLoadUseCase {
    private final LoadChatRoomPort loadChatRoomPort;

    @Override
    public ChatRoomResponse getChatRoomById(ChatRoomQuery chatRoomQuery) {
        PageRequest pageRequest = PageRequest.of(chatRoomQuery.page(), chatRoomQuery.size());
        ChatRoom chatRoom = loadChatRoomPort.loadById(chatRoomQuery.id(), pageRequest);

        return ChatRoomResponse.builder()
                .roomId(chatRoom.getId().value())
                .build();
    }

    @Override
    public ChatRoomListReadResponse getChatRoomList(ChatRoomListQuery query) {
        PageRequest pageRequest = PageRequest.of(query.page(), query.size(), Sort.by("id").descending());
        List<ChatRoom> chatRoomList = loadChatRoomPort.search(pageRequest);
        ChatRoomListReadResponse response = ChatRoomListReadResponse.builder()
                .messageList(chatRoomList.stream().map(chatRoom -> ChatRoomItemResponse.builder()
                                .roomId(chatRoom.getId().value())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        return response;
    }
}