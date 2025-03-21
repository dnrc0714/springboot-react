package com.ccbb.demo.chat.adapter.out.persistence;

import com.ccbb.demo.chat.application.port.out.LoadChatRoomPort;
import com.ccbb.demo.chat.domain.ChatRoom;
import com.ccbb.demo.common.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatRoomLoadPersistenceAdapter implements LoadChatRoomPort {
    private final SpringDataChatRoomRepository springDataChatRoomRepository;
    @Override
    public ChatRoom loadById(Long roomId, PageRequest pageRequest) {
        ChatRoomJpaEntity chatRoomJpaEntity = springDataChatRoomRepository.findById(roomId)
                .orElseThrow(RuntimeException::new);
        return ChatRoom.builder()
                .id(new ChatRoom.id(chatRoomJpaEntity.getId()))
                .build();
    }

    @Override
    public List<ChatRoom> search(PageRequest pageRequest) {
        Slice<ChatRoomJpaEntity> chatRoomJpaEntityList = springDataChatRoomRepository.findAllBy(pageRequest);
        return chatRoomJpaEntityList.stream()
                .map(chatRoomJpaEntity -> ChatRoom.builder()
                        .id(new ChatRoom.id(chatRoomJpaEntity.getId()))
                        .build())
                .collect(Collectors.toList());
    }
}
