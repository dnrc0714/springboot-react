package com.ccbb.demo.comment.application.port.out;

import com.ccbb.demo.comment.domain.Comment;

public interface LoadCommentPort {
    Comment loadById(Long id);
}