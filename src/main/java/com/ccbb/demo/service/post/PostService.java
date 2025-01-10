package com.ccbb.demo.service.post;

import com.ccbb.demo.entity.Post;
import com.ccbb.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
