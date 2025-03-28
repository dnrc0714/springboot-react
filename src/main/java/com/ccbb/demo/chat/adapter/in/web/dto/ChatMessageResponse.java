package com.ccbb.demo.chat.adapter.in.web.dto;

import com.ccbb.demo.chat.domain.UploadFile;
import com.ccbb.demo.entity.UserJpaEntity;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
public record ChatMessageResponse(Long id, String content, List<UploadFile> chatFile, UserJpaEntity creator, Long creatorId, OffsetDateTime createdAt) {
    @Builder
    public ChatMessageResponse {
        // 생성자 정규화 로직 추가 가능
    }
}
