package com.ccbb.demo.controller.post;

import com.ccbb.demo.entity.Post;
import com.ccbb.demo.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/list")
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.ok(postService.getPostList());
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getPostDetail() {

        return ResponseEntity.ok(postService.getPostList());
    }
}
