package com.ccbb.demo.comment.application.port.in.command;

import com.ccbb.demo.comment.adapter.in.web.response.CommentWithRepliesResponse;

public interface CommentLoadUseCase {
    CommentWithRepliesResponse getCommentListByParentId(CommentQuery commentQuery);
}