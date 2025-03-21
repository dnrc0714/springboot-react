package com.ccbb.demo.chat.adapter.in.web.dto;

public record ChatMessageRequest(Long creatorId, String text, String creatorNickName) {
}
