package com.ccbb.demo.chat.domain;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Builder
@Data
public class UploadFile {
    Long id;
    Integer seq;
    String fileName;
    Long fileSize;
    String fileType;
    String s3Url;
    String refreshToken;
    String uploadType;
    OffsetDateTime createdAt;
}
