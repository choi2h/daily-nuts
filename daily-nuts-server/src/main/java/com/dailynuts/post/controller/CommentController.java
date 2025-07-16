package com.dailynuts.post.controller;

import com.dailynuts.post.dto.CommentListResponse;
import com.dailynuts.post.dto.CommentRequest;
import com.dailynuts.post.dto.CommentResponse;
import com.dailynuts.post.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}")
public class CommentController {
    private final CommentService commentService;


    //댓글 등록
    @PostMapping("/comment")
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
    @PostMapping("/comment/{parentCommentId}/reply")
    public ResponseEntity<CommentResponse> createReplyToComment(
            @PathVariable Long postId,
            @PathVariable ObjectId parentCommentId,
            @RequestBody @Valid CommentRequest request
    ) {
        Long memberId = 1L; // 테스트용 하드코딩
        String writer = request.getWriter();

        CommentResponse response = commentService.createReplyToComment(postId, parentCommentId, memberId, writer, request);
        return ResponseEntity.ok(response);
    }

    //댓글+ 대댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<CommentListResponse> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
        CommentListResponse response = new CommentListResponse(comments);
        return ResponseEntity.ok(response);
    }

    //댓글 수정
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable String commentId,
            @RequestBody @Valid CommentRequest request
    ){
        Long memberId=1L; //로그인 사용자 대체
        CommentResponse response = commentService.updateComment(postId,commentId,memberId,request);
        return ResponseEntity.ok(response);
    }
}