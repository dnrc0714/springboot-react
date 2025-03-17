package com.ccbb.demo.comment.application.port.in.command;

import lombok.Builder;

@Builder
public record CommentQuery(Long postId, Long commentId, String content) {
}