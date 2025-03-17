package com.ccbb.demo.comment.application.port.out;

import com.ccbb.demo.comment.domain.Comment;

public interface CreateCommentPort {
    boolean createComment(Comment comment);
}