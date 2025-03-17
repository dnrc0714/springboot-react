package com.ccbb.demo.comment.application.port.out;

import com.ccbb.demo.comment.domain.Comment;

public interface CreateCommentReplyPort {
    boolean createCommentReply(Comment comment);
}