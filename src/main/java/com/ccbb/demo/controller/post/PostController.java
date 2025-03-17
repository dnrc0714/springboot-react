package com.ccbb.demo.controller.post;

import com.ccbb.demo.dto.post.PostRequest;
import com.ccbb.demo.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/list")
    public ResponseEntity<?> getPosts(@RequestParam("postTp") String postTp) {
        return ResponseEntity.ok(postService.getPostList(postTp));
    }

    @PostMapping(value = "/detail")
    public ResponseEntity<?> getPostDetail(@RequestParam("postId") Long id) {
        return ResponseEntity.ok(postService.getPostDetail(id));
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> savePost(@ModelAttribute @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.savePost(request));
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> deletePost(@RequestParam("postId") Long id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }
}