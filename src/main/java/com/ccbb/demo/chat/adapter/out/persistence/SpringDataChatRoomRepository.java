package com.ccbb.demo.chat.adapter.out.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataChatRoomRepository extends JpaRepository<ChatRoomJpaEntity, Long> {
    Slice<ChatRoomJpaEntity> findAllBy(Pageable pageable);
}