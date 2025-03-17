package com.ccbb.demo.comment.domain;


import com.ccbb.demo.entity.PostJpaEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Comment {
    private final CommentId id;
    private final String content;
    private final PostJpaEntity.PostId postId;
    private final CommentId parentId;
    private final List<Comment> replies;
    public record CommentId(Long value){}
}