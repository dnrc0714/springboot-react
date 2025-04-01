package com.ccbb.demo.chat.adapter.out.persistence;

import com.ccbb.demo.chat.application.port.out.CreateChatFilePort;
import com.ccbb.demo.chat.domain.ChatFiles;
import com.ccbb.demo.common.annotation.PersistenceAdapter;
import com.ccbb.demo.entity.ChatFileJpaEntity;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.util.FileUtil;
import com.ccbb.demo.util.GcpFileUtil;
import com.ccbb.demo.util.JwtUtil;
import com.ccbb.demo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@PersistenceAdapter
@RequiredArgsConstructor
public class ChatFilePersistenceAdapter implements CreateChatFilePort {

    private final GcpFileUtil fileUtil;
    private final JwtUtil jwtUtil;
    private final SpringDataChatFileRepository springDataChatFileRepository;


    @Override
    public List<ChatFileJpaEntity> createChatFiles(ChatFiles chatFiles) {
        UserJpaEntity user = new UserJpaEntity();

        Map<String, String> tokenInfo = jwtUtil.jwtData(chatFiles.getToken());
        user.setId(tokenInfo.get("id"));
        user.setUsername(tokenInfo.get("username"));
        user.setUserId(Long.parseLong(tokenInfo.get("userId")));
        user.setNickname(tokenInfo.get("nickname"));
        user.setRole(tokenInfo.get("role"));

        List<ChatFileJpaEntity> chatFilesArr = new ArrayList<>();

        String dirName = "chat-files/" + chatFiles.getChatRoomId() + "/" + chatFiles.getId();

        if(chatFiles.getFiles() != null) {
            AtomicInteger seq = new AtomicInteger(1);
            chatFiles.getFiles().forEach(file -> {
                String fileUrl = null;
                try {
                    fileUrl = fileUtil.uploadFile(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ChatFileJpaEntity chatFileJpaEntity = chatFileJpaBuilder(file, chatFiles.getId(), seq.get(), fileUrl, user.getUserId());

                chatFilesArr.add(springDataChatFileRepository.save(chatFileJpaEntity));

                seq.getAndIncrement();
            });
        }
        return chatFilesArr;
    }

    @Override
    public ChatFileJpaEntity createChatFile(ChatFiles chatFiles) {
        UserJpaEntity user = new UserJpaEntity();

        Map<String, String> tokenInfo = jwtUtil.jwtData(chatFiles.getToken());
        user.setId(tokenInfo.get("id"));
        user.setUsername(tokenInfo.get("username"));
        user.setUserId(Long.parseLong(tokenInfo.get("userId")));
        user.setNickname(tokenInfo.get("nickname"));
        user.setRole(tokenInfo.get("role"));

        ChatFileJpaEntity chatFileJpaEntity = new ChatFileJpaEntity();

        String dirName = "chat-files/" + chatFiles.getChatRoomId() + "/" + chatFiles.getId();
        try {
            if(chatFiles.getFile() != null) {
                MultipartFile file = chatFiles.getFile();
                String fileUrl = fileUtil.uploadFile(file);

                chatFileJpaEntity = chatFileJpaBuilder(file, chatFiles.getId(), 1, fileUrl, user.getUserId());
                return springDataChatFileRepository.save(chatFileJpaEntity);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chatFileJpaEntity;
    }

    public ChatFileJpaEntity chatFileJpaBuilder(MultipartFile file, Long chatId, int seq, String fileUrl, Long userId) {
        return ChatFileJpaEntity.builder()
               .chatMessageId(chatId)
               .fileName(file.getOriginalFilename())
               .seq(seq)
               .fileSize(file.getSize())
               .fileType(file.getContentType())
               .s3Url(fileUrl)
               .uploadedBy(userId)
               .build();
    }
}
