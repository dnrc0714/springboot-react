package com.ccbb.demo.service.post;

import com.ccbb.demo.dto.post.PostRequest;
import com.ccbb.demo.entity.PostJpaEntity;
import com.ccbb.demo.entity.UserJpaEntity;
import com.ccbb.demo.repository.PostRepository;
import com.ccbb.demo.service.auth.AuthService;
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

    public List<PostJpaEntity> getPostList(String postTp) {
        List<PostJpaEntity> postList = new ArrayList<>();

        try {
            postList = postRepository.findByPostTp(postTp);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return postList;
    }

    public PostJpaEntity getPostDetail(Long postId) {
            PostJpaEntity post = postRepository.findByPostId(postId)
                    .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다.: "));

        return post;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PostJpaEntity savePost(PostRequest request){
        UserJpaEntity user = authService.jwtTokenToUser(request.getRefreshToken());

            long userId = user.getUserId();

            PostJpaEntity.PostJpaEntityBuilder postBuilder = PostJpaEntity.builder()
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
            PostJpaEntity post = postBuilder.build();
            postRepository.save(post);

            return post;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int deletePost(Long postId) {
        return postRepository.deleteByPostId(postId);
    }
}
