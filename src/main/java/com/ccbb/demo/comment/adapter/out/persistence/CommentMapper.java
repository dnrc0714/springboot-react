package com.ccbb.demo.comment.adapter.out.persistence;

import com.ccbb.demo.comment.domain.Comment;
import com.ccbb.demo.common.annotation.Mapper;
import com.ccbb.demo.comment.domain.Post;
import com.ccbb.demo.entity.PostJpaEntity;

import java.util.Optional;
import java.util.stream.Collectors;

@Mapper
class CommentMapper {
    public CommentJpaEntity domainToEntity(Comment comment, PostJpaEntity post){
        return CommentJpaEntity.builder()
                .content(comment.getContent())
                .post(post)
                .build();
    }
    public CommentJpaEntity domainToEntityWithParent(Comment comment, CommentJpaEntity parentComment, PostJpaEntity post){
        return CommentJpaEntity.builder()
                .content(comment.getContent())
                .parent(parentComment)
                .post(post)
                .build();
    }
    public Comment entityToDomain(CommentJpaEntity commentJpaEntity){ // 댓글 목록만
        return Comment.builder()
                .id(new Comment.CommentId(commentJpaEntity.getCommentId()))
                .content(commentJpaEntity.getContent())
                .postId(new PostJpaEntity.PostId(commentJpaEntity.getPost().getPostId()))
                .parentId(new Comment.CommentId(Optional.ofNullable(commentJpaEntity.getParent())
                        .map(CommentJpaEntity::getCommentId)
                        .orElse(null)))
                .build();
    }
    public Comment entityToDomainWithReplies(CommentJpaEntity commentJpaEntity){ // 대댓글 목록까지
        return Comment.builder()
                .id(new Comment.CommentId(commentJpaEntity.getCommentId()))
                .content(commentJpaEntity.getContent())
                .postId(new PostJpaEntity.PostId(commentJpaEntity.getPost().getPostId()))
                .parentId(new Comment.CommentId(Optional.ofNullable(commentJpaEntity.getParent())
                        .map(CommentJpaEntity::getCommentId)
                        .orElse(null)))
                .replies(commentJpaEntity.getReplies().stream()
                        .map(reply -> Comment.builder()
                                .id(new Comment.CommentId(reply.getCommentId()))
                                .content(reply.getContent())
                                .postId(new PostJpaEntity.PostId(reply.getPost().getPostId()))
                                .parentId(new Comment.CommentId(
                                        Optional.ofNullable(reply.getParent())
                                                .map(CommentJpaEntity::getCommentId)
                                                .orElse(null)))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}