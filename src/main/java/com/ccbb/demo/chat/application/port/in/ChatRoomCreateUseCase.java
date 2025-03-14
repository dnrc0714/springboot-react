package com.ccbb.demo.chat.application.port.in;


import com.ccbb.demo.chat.application.port.in.command.ChatRoomCreateCommand;

public interface ChatRoomCreateUseCase {

    boolean createChatRoom(ChatRoomCreateCommand command);
}