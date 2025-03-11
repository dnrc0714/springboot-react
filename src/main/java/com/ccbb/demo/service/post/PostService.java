package com.ccbb.demo.service.post;

import com.ccbb.demo.dto.post.PostRequest;
import com.ccbb.demo.entity.Post;
import com.ccbb.demo.entity.User;
import com.ccbb.demo.repository.PostRepository;
import com.ccbb.demo.repository.UserRepository;
import com.ccbb.demo.service.auth.AuthService;
import com.ccbb.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final AuthService authService;

    public List<Post> getPostList(String postTp) {
        List<Post> postList = new ArrayList<>();

        try {
            postList = postRepository.findByPostTp(postTp);
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
    public Post savePost(PostRequest request){
        User user = authService.jwtTokenToUser(request.getRefreshToken());

            long userId = user.getUserId();

            Post.PostBuilder postBuilder = Post.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .postTp(request.getPostTp())
                    .creatorId(userId)
                    .createdAt(OffsetDateTime.now())
                    .updaterId(userId)
                    .updatedAt(OffsetDateTime.now());
            if(request.getPostId() != null) {
                postBuilder.postId(request.getPostId());
            }
            Post post = postBuilder.build();
            postRepository.save(post);

            return post;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int deletePost(Long postId) {
        return postRepository.deleteByPostId(postId);
    }
}
