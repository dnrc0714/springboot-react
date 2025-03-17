package com.ccbb.demo.comment.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Post {
    private final PostId id;
    private final String title;
    private final String content;
    private final List<Comment> comments;
    public record PostId(Long value) {}
}