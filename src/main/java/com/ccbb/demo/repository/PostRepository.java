package com.ccbb.demo.repository;

import com.ccbb.demo.entity.PostJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostJpaEntity, Long> {
    Optional<PostJpaEntity> findByPostId(Long id);

    List<PostJpaEntity> findByPostTp(String postTp);

    int deleteByPostId(Long id);
}
