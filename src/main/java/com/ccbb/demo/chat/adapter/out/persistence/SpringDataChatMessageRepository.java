package com.ccbb.demo.chat.adapter.out.persistence;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SpringDataChatMessageRepository extends JpaRepository<ChatMessageJpaEntity, Long> {

    @EntityGraph(attributePaths = {"creator"})
    Slice<ChatMessageJpaEntity> findAllByChatRoom(ChatRoomJpaEntity chatRoomJpaEntity, PageRequest pageRequest);

    @EntityGraph(attributePaths = {"creator"})
    ChatMessageJpaEntity findOneById(Long id);
}