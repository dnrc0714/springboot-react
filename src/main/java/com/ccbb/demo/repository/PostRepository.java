package com.ccbb.demo.repository;

import com.ccbb.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long id);

    int deleteByPostId(Long id);
}
