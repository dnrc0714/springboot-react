package com.ccbb.demo.controller.post;

import com.ccbb.demo.dto.post.PostRequest;
import com.ccbb.demo.entity.Post;
import com.ccbb.demo.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/list")
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.ok(postService.getPostList());
    }

    @PostMapping(value = "/detail")
    public ResponseEntity<?> getPostDetail(@RequestParam Long id) {
        return ResponseEntity.ok(postService.getPostDetail(id));
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> savePost(@ModelAttribute @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.savePost(request));
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deletePost(@RequestParam Long id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }
}