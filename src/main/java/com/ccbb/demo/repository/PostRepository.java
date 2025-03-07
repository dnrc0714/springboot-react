package com.ccbb.demo.repository;

import com.ccbb.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long id);

    List<Post> findByPostTp(String postTp);

    int deleteByPostId(Long id);
}
