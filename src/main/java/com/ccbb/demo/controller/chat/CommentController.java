package com.ccbb.demo.controller.chat;
import com.ccbb.demo.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class CommentController {
    private final CommentCreateUseCase createCommentUseCase;
    private final CommentLoadUseCase commentLoadUseCase;
    @PostMapping("/{postId}/comments")
    public SuccessApiResponse<?> createComment(@RequestBody CommentCreateRequest createRequest, @PathVariable Long postId){
        CommentCreateCommand commentCreateCommand = CommentCreateCommand.builder()
                .content(createRequest.content())
                .postId(postId)
                .build();
        return SuccessApiResponse.of(createCommentUseCase.createComment(commentCreateCommand));
    }
    @PostMapping("/{postId}/comments/{commentId}")
    public SuccessApiResponse<?> createReplyComment(@RequestBody CommentCreateRequest createRequest, @PathVariable Long postId, @PathVariable Long commentId){
        CommentReplyCreateCommand commentCreateCommand = CommentReplyCreateCommand.builder()
                .content(createRequest.content())
                .parentId(commentId)
                .postId(postId)
                .build();
        return SuccessApiResponse.of(createCommentUseCase.createCommentReply(commentCreateCommand));
    }
    @GetMapping("/{postId}/comments/{commentId}")
    public SuccessApiResponse<?> getCommentByCommentId(@PathVariable Long postId, @PathVariable Long commentId){
        CommentQuery getCommentQuery = CommentQuery.builder()
                .commentId(commentId)
                .postId(postId)
                .build();
        return SuccessApiResponse.of(commentLoadUseCase.getCommentListByParentId(getCommentQuery));
    }
}