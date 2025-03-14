package com.ccbb.demo.chat.application.port.in.query;

import lombok.Builder;

@Builder
public record ChatRoomListQuery(int page, int size) {
}