package com.ccbb.demo.chat.adapter.out.persistence;

import com.ccbb.demo.common.annotation.PersistenceAdapter;
import com.ccbb.demo.chat.application.port.out.CreateChatMessagePort;
import com.ccbb.demo.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public class ChatMessagePersistenceAdapter implements CreateChatMessagePort {
    private final SpringDataChatRoomRepository springDataChatRoomRepository;

    @Override
    @Transactional
    public Long createChatMessage(ChatMessage chatMessage) {
        ChatRoomJpaEntity chatRoomJpaEntity = springDataChatRoomRepository.findById(chatMessage.getChatRoomId().value())
                .orElseThrow(RuntimeException::new);

        ChatMessageJpaEntity chatMessageJpaEntity = ChatMessageJpaEntity.builder()
                .chatRoom(chatRoomJpaEntity)
                .content(chatMessage.getContent())
                .creatorId(chatMessage.getCreatorId())
                .build();

        chatRoomJpaEntity.createMessage(chatMessageJpaEntity);
        springDataChatRoomRepository.save(chatRoomJpaEntity);

        return chatMessageJpaEntity.getId();
    }
}
