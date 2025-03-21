package com.ccbb.demo.chat.adapter.in.web.dto;

import com.ccbb.demo.entity.UserJpaEntity;
import lombok.Builder;

@Builder
public record ChatMessageResponse(Long id, String content, String creatorNickName) {
}
