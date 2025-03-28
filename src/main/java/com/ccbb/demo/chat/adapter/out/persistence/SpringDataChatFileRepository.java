package com.ccbb.demo.chat.adapter.out.persistence;

import com.ccbb.demo.entity.ChatFileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataChatFileRepository extends JpaRepository<ChatFileJpaEntity, Long> {
}
