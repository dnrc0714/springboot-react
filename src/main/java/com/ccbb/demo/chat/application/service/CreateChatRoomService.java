package com.ccbb.demo.chat.application.service;
import com.ccbb.demo.chat.application.port.in.command.ChatRoomCreateCommand;
import com.ccbb.demo.chat.domain.ChatRoom;
import com.ccbb.demo.chat.application.port.in.ChatRoomCreateUseCase;
import com.ccbb.demo.chat.application.port.out.CreateChatRoomPort;
import com.ccbb.demo.common.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateChatRoomService implements ChatRoomCreateUseCase {
    private final CreateChatRoomPort createChatRoomPort;

    @Override
    @Transactional
    public boolean createChatRoom(ChatRoomCreateCommand command) {
        ChatRoom chatRoom = ChatRoom.builder()
                .build();
        return createChatRoomPort.createChatRoom(chatRoom);
    }
}