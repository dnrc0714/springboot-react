package com.ccbb.demo.chat.adapter.in.web.dto;

import lombok.Builder;

@Builder
public record ChatMessageResponse(Long id, String content, String writer) {
}
