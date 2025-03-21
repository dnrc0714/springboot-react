package com.ccbb.demo.chat.adapter.out.persistence;

import com.ccbb.demo.chat.application.port.out.CreateChatRoomPort;
import com.ccbb.demo.chat.domain.ChatRoom;
import com.ccbb.demo.common.annotation.PersistenceAdapter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatRoomPersistenceAdapter implements CreateChatRoomPort {
    private final SpringDataChatRoomRepository springDataChatRoomRepository;

    @Transactional
    @Override
    public boolean createChatRoom(ChatRoom chatRoom) {
        ChatRoomJpaEntity chatRoomJpaEntity = ChatRoomJpaEntity.builder()
                .name(chatRoom.getName())
                .build();
        springDataChatRoomRepository.save(chatRoomJpaEntity);
        return true;
    }
}
