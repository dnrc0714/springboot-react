package com.ccbb.demo.chat.adapter.in.web.dto;

import com.ccbb.demo.entity.UserJpaEntity;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ChatMessageResponse(Long id, String content, UserJpaEntity creator, Long creatorId, OffsetDateTime createdAt) {
}
