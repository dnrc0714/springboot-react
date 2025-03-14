package com.ccbb.demo.chat.application.port.in;

import com.ccbb.demo.chat.adapter.in.web.dto.ChatRoomListReadResponse;
import com.ccbb.demo.chat.adapter.in.web.dto.ChatRoomResponse;
import com.ccbb.demo.chat.application.port.in.query.ChatRoomListQuery;
import com.ccbb.demo.chat.application.port.in.query.ChatRoomQuery;

public interface ChatRoomLoadUseCase {
    ChatRoomResponse getChatRoomById(ChatRoomQuery chatRoomQuery);
    ChatRoomListReadResponse getChatRoomList(ChatRoomListQuery query);
}