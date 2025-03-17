package com.ccbb.demo.comment.adapter.out.persistence;

import com.ccbb.demo.comment.application.port.out.LoadCommentPort;
import com.ccbb.demo.comment.domain.Comment;
import com.ccbb.demo.common.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class CommentLoadPersistenceAdapter implements LoadCommentPort {
    private final SpringDataCommentRepository springDataCommentRepository;
    private final CommentMapper commentMapper;
    @Override
    public Comment loadById(Long id) {
        CommentJpaEntity commentJpaEntity = springDataCommentRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        return commentMapper.entityToDomainWithReplies(commentJpaEntity);
    }
}