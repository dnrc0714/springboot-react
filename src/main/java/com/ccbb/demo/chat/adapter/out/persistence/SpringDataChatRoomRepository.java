package com.ccbb.demo.chat.adapter.out.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SpringDataChatRoomRepository extends JpaRepository<ChatRoomJpaEntity, Long> {

    Slice<ChatRoomJpaEntity> findAllBy(Pageable pageable);
}