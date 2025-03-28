package com.ccbb.demo.chat.adapter.in.web.dto;

import com.ccbb.demo.chat.domain.ChatContent;

public record ChatMessageRequest(ChatContent content, String from, String type) {
}