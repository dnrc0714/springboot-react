package com.ccbb.demo.chat.adapter.out.persistence;

import com.ccbb.demo.common.annotation.PersistenceAdapter;
import com.ccbb.demo.chat.application.port.out.CreateChatMessagePort;
import com.ccbb.demo.chat.domain.ChatMessage;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public class ChatMessagePersistenceAdapter implements CreateChatMessagePort {

    private final SpringDataChatRoomRepository springDataChatRoomRepository;
    private final SpringDataChatMessageRepository springDataChatMessageRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public ChatMessageJpaEntity createChatMessage(ChatMessage chatMessage) {
        ChatRoomJpaEntity chatRoomJpaEntity = springDataChatRoomRepository.findById(chatMessage.getChatRoomId().value())
                .orElseThrow(RuntimeException::new);

        UserJpaEntity user = new UserJpaEntity();
        if(chatMessage.getPrincipal() != null) {
            if (chatMessage.getPrincipal() instanceof UsernamePasswordAuthenticationToken authToken) {
                Object userPrincipal = authToken.getPrincipal();

                if (userPrincipal instanceof UserJpaEntity) {
                    user = (UserJpaEntity) userPrincipal;
                }
            }
        } else {
            Map<String, String> tokenInfo = jwtUtil.jwtData(chatMessage.getToken());

            user.setId(tokenInfo.get("id"));
            user.setUsername(tokenInfo.get("username"));
            user.setUserId(Long.parseLong(tokenInfo.get("userId")));
            user.setNickname(tokenInfo.get("nickname"));
            user.setRole(tokenInfo.get("role"));
        }

        /*파일이 유무 관계없이 채팅 테이블에 저장은 해야함*/
        ChatMessageJpaEntity chatMessageJpaEntity = ChatMessageJpaEntity.builder()
                .chatRoom(chatRoomJpaEntity)
                .content(chatMessage.getContent())
                .creator(user)
                .type(chatMessage.getType())
                .creatorId(user.getUserId())
                .build();

        chatMessageJpaEntity = springDataChatMessageRepository.save(chatMessageJpaEntity);

        chatRoomJpaEntity.createMessage(chatMessageJpaEntity);

        return chatMessageJpaEntity;
    }


}
