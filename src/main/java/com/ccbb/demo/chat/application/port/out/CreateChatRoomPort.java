package com.ccbb.demo.chat.application.port.out;

import com.ccbb.demo.chat.domain.ChatRoom;

public interface CreateChatRoomPort {
    boolean createChatRoom(ChatRoom chatRoom);
}