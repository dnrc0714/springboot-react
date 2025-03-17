package com.ccbb.demo.comment.adapter.out.persistence;

import com.ccbb.demo.comment.application.port.out.CreateCommentPort;
import com.ccbb.demo.comment.application.port.out.CreateCommentReplyPort;
import com.ccbb.demo.comment.domain.Comment;
import com.ccbb.demo.common.annotation.PersistenceAdapter;
import com.ccbb.demo.entity.PostJpaEntity;
import com.ccbb.demo.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@PersistenceAdapter
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CreateCommentPort, CreateCommentReplyPort {
    private final SpringDataCommentRepository springDataCommentRepository;
    private final PostRepository springDataPostRepository;
    private final CommentMapper commentMapper;

    @Transactional
    @Override
    public boolean createComment(Comment comment) {
        PostJpaEntity postJpaEntity = springDataPostRepository.findById(comment.getPostId().value())
                .orElseThrow(RuntimeException::new);
        CommentJpaEntity commentJpaEntity = commentMapper.domainToEntity(comment, postJpaEntity);
        postJpaEntity.createComment(commentJpaEntity);
        springDataPostRepository.save(postJpaEntity);
        return true;
    }

    @Transactional
    @Override
    public boolean createCommentReply(Comment comment) {
        PostJpaEntity postJpaEntity = springDataPostRepository.findById(comment.getPostId().value())
                .orElseThrow(RuntimeException::new);
        CommentJpaEntity parentCommentJpaEntity = springDataCommentRepository.findById(comment.getParentId().value())
                .orElseThrow(RuntimeException::new);
        CommentJpaEntity commentJpaEntity = commentMapper.domainToEntityWithParent(comment, parentCommentJpaEntity, postJpaEntity);
        parentCommentJpaEntity.addReply(commentJpaEntity);
        springDataCommentRepository.save(parentCommentJpaEntity);
        return true;
    }
}