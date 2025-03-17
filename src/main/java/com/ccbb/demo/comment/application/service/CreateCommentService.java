package com.ccbb.demo.comment.application.service;

import com.ccbb.demo.comment.application.port.in.CommentCreateUseCase;
import com.ccbb.demo.comment.application.port.in.command.CommentCreateCommand;
import com.ccbb.demo.comment.application.port.in.command.CommentReplyCreateCommand;
import com.ccbb.demo.comment.application.port.out.CreateCommentPort;
import com.ccbb.demo.comment.application.port.out.CreateCommentReplyPort;
import com.ccbb.demo.comment.domain.Comment;
import com.ccbb.demo.common.annotation.UseCase;
import com.ccbb.demo.entity.PostJpaEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
class CreateCommentService implements CommentCreateUseCase {
    private final CreateCommentPort createCommentPort;
    private final CreateCommentReplyPort createCommentReply;
    @Override
    public boolean createComment(CommentCreateCommand commentCreateCommand) {
        Comment comment = Comment.builder()
                .content(commentCreateCommand.content())
                .postId(new PostJpaEntity.PostId(commentCreateCommand.postId()))
                .build();
        createCommentPort.createComment(comment);
        return true;
    }

    @Override
    public boolean createCommentReply(CommentReplyCreateCommand commentCreateCommand) {
        Comment comment = Comment.builder()
                .content(commentCreateCommand.content())
                .postId(new PostJpaEntity.PostId(commentCreateCommand.postId()))
                .parentId(new Comment.CommentId(commentCreateCommand.parentId()))
                .build();
        createCommentReply.createCommentReply(comment);
        return true;
    }
}