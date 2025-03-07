package com.ccbb.demo.dto.post;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostRequest {
    private Long postId;
    private String postTp;
    private String title;
    private String content;
    private String refreshToken;
}
