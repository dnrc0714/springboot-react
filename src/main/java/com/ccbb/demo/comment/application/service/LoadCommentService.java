package com.ccbb.demo.comment.application.service;

import com.ccbb.demo.comment.adapter.in.web.response.CommentResponse;
import com.ccbb.demo.comment.adapter.in.web.response.CommentWithRepliesResponse;
import com.ccbb.demo.comment.application.port.in.command.CommentLoadUseCase;
import com.ccbb.demo.comment.application.port.in.command.CommentQuery;
import com.ccbb.demo.comment.application.port.out.LoadCommentPort;
import com.ccbb.demo.comment.domain.Comment;
import com.ccbb.demo.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@UseCase
public class LoadCommentService implements CommentLoadUseCase {
    private final LoadCommentPort loadCommentPort;
    @Override
    public CommentWithRepliesResponse getCommentListByParentId(CommentQuery commentQuery) {
        Comment comment = loadCommentPort.loadById(commentQuery.commentId());

        return CommentWithRepliesResponse.builder()
                .commentId(comment.getId().value())
                .content(comment.getContent())
                .postId(comment.getPostId().value())
                .parentId(comment.getParentId().value())
                .replies(comment.getReplies().stream()
                        .map((domain)-> CommentResponse.builder()
                                .commentId(domain.getId().value())
                                .content(domain.getContent())
                                .postId(domain.getPostId().value())
                                .parentId(domain.getParentId().value())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}