package com.dailynuts.post.controller;

import com.dailynuts.post.dto.CommentRequest;
import com.dailynuts.post.dto.CommentResponse;
import com.dailynuts.post.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment")
public class CommentController {
    private final CommentService commentService;


    //댓글 등록
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentRequest request
    ) {
        Long memberId = 1L; // 임시
        String writer = request.getWriter();

        CommentResponse response = commentService.createComment(postId, memberId, writer, request);
        return ResponseEntity.ok(response);
    }

    //대댓글 등록
    @PostMapping("/{parentCommentId}/reply")
    public ResponseEntity<CommentResponse> createReplyToComment(
            @PathVariable Long postId,
            @PathVariable String parentCommentId,
            @RequestBody @Valid CommentRequest request
    ) {
        Long memberId = 1L; // 테스트용 하드코딩
        String writer = request.getWriter();

        CommentResponse response = commentService.createReplyToComment(postId, parentCommentId, memberId, writer, request);
        return ResponseEntity.ok(response);
    }

    //댓글+ 대댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }
}