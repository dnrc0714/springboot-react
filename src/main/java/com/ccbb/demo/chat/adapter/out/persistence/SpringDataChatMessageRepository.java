package com.ccbb.demo.chat.adapter.out.persistence;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface SpringDataChatMessageRepository extends JpaRepository<ChatMessageJpaEntity, Long> {

    // @EntityGraph(attributePaths = {"creator"})
    Slice<ChatMessageJpaEntity> findAllByChatRoom(
            ChatRoomJpaEntity chatRoomJpaEntity,
            Pageable pageable // ✅ PageRequest 말고 Pageable로 명시
    );

    @EntityGraph(attributePaths = {"creator", "files"})
    @Query("SELECT m FROM ChatMessageJpaEntity m WHERE m.chatRoom = :chatRoom AND m.id < :lastMessageId ORDER BY m.id DESC")
    Slice<ChatMessageJpaEntity> findByChatRoomAndIdLessThan(
            @Param("chatRoom") ChatRoomJpaEntity chatRoom,
            @Param("lastMessageId") Long lastMessageId,
            Pageable pageable);
}