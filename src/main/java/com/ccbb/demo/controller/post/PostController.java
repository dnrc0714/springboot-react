package com.ccbb.demo.controller.post;

import com.ccbb.demo.entity.Post;
import com.ccbb.demo.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getPostDetail(Long id) {
        return ResponseEntity.ok(postService.getPostDetail(id));
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePost(@RequestPart(value = "title", required = true) String title,
                                      @RequestPart(value = "content", required = true) String content) {
        return ResponseEntity.ok(postService.savePost(title, content));
    }
}