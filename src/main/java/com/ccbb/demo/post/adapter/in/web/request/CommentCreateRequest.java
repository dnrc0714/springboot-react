package com.ccbb.demo.post.adapter.in.web.request;

import lombok.Builder;

@Builder
public record CommentCreateRequest(String content) {
}