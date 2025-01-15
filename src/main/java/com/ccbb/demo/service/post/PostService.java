package com.ccbb.demo.service.post;

import com.ccbb.demo.entity.Post;
import com.ccbb.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getPostList() {
        List<Post> postList = new ArrayList<>();
        try {
            postList = postRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return postList;
    }

    public Post getPostDetail(Long postId) {
            Post post = postRepository.findByPostId(postId)
                    .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다.: "));

        return post;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Post savePost(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        return postRepository.save(post);
    }



}
