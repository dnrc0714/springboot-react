package com.ccbb.demo.chat.adapter.in.web.dto;

import com.ccbb.demo.chat.domain.UploadFile;
import com.ccbb.demo.entity.ChatFileJpaEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatFileMapper {
    public List<UploadFile> convertToUploadFiles(List<ChatFileJpaEntity> chatFiles) {
        return chatFiles.stream()
                .map(chatFile -> UploadFile.builder()
                        .id(chatFile.getId())
                        .seq(chatFile.getSeq())
                        .fileName(chatFile.getFileName())
                        .fileSize(chatFile.getFileSize())
                        .fileType(chatFile.getFileType())
                        .s3Url(chatFile.getS3Url())
                        .createdAt(chatFile.getCreatedAt().atOffset(ZoneOffset.UTC))
                        .build())
                .collect(Collectors.toList());
    }
}