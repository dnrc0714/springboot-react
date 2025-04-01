package com.ccbb.demo.chat.application.port.out;


import com.ccbb.demo.chat.domain.ChatFiles;
import com.ccbb.demo.entity.ChatFileJpaEntity;

import java.util.List;

public interface CreateChatFilePort {
    List<ChatFileJpaEntity> createChatFiles(ChatFiles chatFiles);

    ChatFileJpaEntity createChatFile(ChatFiles chatFiles);
}
