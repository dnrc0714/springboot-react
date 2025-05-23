package com.ccbb.demo.chat.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class ChatFiles {
    Long id;
    List<MultipartFile> files;
    MultipartFile file;
    String uploadType;  //  img, video, file
    String type;        //  001, 002,    003
    String token;
    Long chatRoomId;
}
