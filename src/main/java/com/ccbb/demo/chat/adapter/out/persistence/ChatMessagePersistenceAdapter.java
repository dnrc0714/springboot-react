package com.ccbb.demo.chat.adapter.out.persistence;

import com.ccbb.demo.common.annotation.PersistenceAdapter;
import com.ccbb.demo.chat.application.port.out.CreateChatMessagePort;
import com.ccbb.demo.chat.domain.ChatMessage;
import com.ccbb.demo.entity.ChatFileJpaEntity;
import com.ccbb.demo.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public class ChatMessagePersistenceAdapter implements CreateChatMessagePort {
    private final FileUtil fileUtil;
    private final SpringDataChatRoomRepository springDataChatRoomRepository;
    private final SpringDataChatFileRepository springDataChatFileRepository;

    @Override
    @Transactional
    public ChatMessageJpaEntity createChatMessage(ChatMessage chatMessage) {
        ChatRoomJpaEntity chatRoomJpaEntity = springDataChatRoomRepository.findById(chatMessage.getChatRoomId().value())
                .orElseThrow(RuntimeException::new);

        /*파일이 유무 관계없이 채팅 테이블에 저장은 해야함*/
        ChatMessageJpaEntity chatMessageJpaEntity = ChatMessageJpaEntity.builder()
                .chatRoom(chatRoomJpaEntity)
                .content(chatMessage.getContent())
                .creator(chatMessage.getCreator())
                .type(chatMessage.getType())
                .creatorId(chatMessage.getCreatorId())
                .build();

        chatRoomJpaEntity.createMessage(chatMessageJpaEntity);
        springDataChatRoomRepository.save(chatRoomJpaEntity);

        // AWS S3 업로드
        String dirName = "chat-files/" + chatMessageJpaEntity.getChatRoom().getId() + "/" + chatMessageJpaEntity.getId();
        if (chatMessage.getFiles() != null) {
            int seq = 1;
            String uploadUrl = "";
            for(MultipartFile file : chatMessage.getFiles()) {
                try {
                    uploadUrl = fileUtil.upload(file, dirName);
                } catch (IOException e) {
                    throw new RuntimeException("파일업로드에 실패했습니다. : " + e.getMessage());
                }
                // DB 첨부파일 저장 METADATA
                ChatFileJpaEntity chatFileJpaEntity = ChatFileJpaEntity.builder()
                        .id(chatMessage.getChatId().value())
                        .seq(seq++)
                        .fileName(file.getName())
                        .fileSize(file.getSize())
                        .fileType(file.getContentType())
                        .s3Url(uploadUrl)
                        .uploadedBy(chatMessage.getCreatorId())
                        .build();
                try {
                    springDataChatFileRepository.save(chatFileJpaEntity);
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            }
        }



        return chatMessageJpaEntity;
    }
}
