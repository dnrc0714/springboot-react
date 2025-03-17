package com.ccbb.demo.comment.application.port.in.command;

import lombok.Builder;

@Builder
public record CommentReplyCreateCommand(String content, Long postId, Long parentId) {
}