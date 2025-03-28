package com.ccbb.demo.chat.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ChatContent {
    private String text;
    private MultipartFile file;
    private String type;
    private List<MultipartFile> files;

    public boolean isText() {
        return type.equals("001") ;
    }

    public boolean isImage() {
        return type.equals("002") ;
    }

    public boolean isVideo() {
        return type.equals("003") ;
    }

    public boolean isFile() {
        return type.equals("004") ;
    }
}
