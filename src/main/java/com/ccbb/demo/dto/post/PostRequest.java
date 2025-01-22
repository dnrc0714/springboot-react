package com.ccbb.demo.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PostRequest {
    private int postTp;
    private String title;
    private String content;
    private String refreshToken;
}
