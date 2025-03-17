package com.ccbb.demo.comment.adapter.in.web.request;

import lombok.Builder;

@Builder
public record CommentCreateRequest(String content) {
}