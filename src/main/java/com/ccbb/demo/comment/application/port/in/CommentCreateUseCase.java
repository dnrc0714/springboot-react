package com.ccbb.demo.comment.application.port.in;

import com.ccbb.demo.comment.application.port.in.command.CommentCreateCommand;
import com.ccbb.demo.comment.application.port.in.command.CommentReplyCreateCommand;

public interface CommentCreateUseCase {
    boolean createComment(CommentCreateCommand commentCreateCommand);
    boolean createCommentReply(CommentReplyCreateCommand commentCreateCommand);
}