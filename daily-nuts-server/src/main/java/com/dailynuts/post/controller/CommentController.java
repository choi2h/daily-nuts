package com.dailynuts.post.controller;

import com.dailynuts.post.dto.CommentRequestDto;
import com.dailynuts.post.dto.CommentResponseDto;
import com.dailynuts.post.dto.CommentsResponseDto;
import com.dailynuts.post.dto.DeleteResponseDto;
import com.dailynuts.post.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}")
public class CommentController {
    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentRequestDto request
    ) {
        Long memberId = 1L; // 임시
        String writer = request.getWriter();

        CommentResponseDto response = commentService.createComment(postId, memberId, writer, request);
        return ResponseEntity.ok(response);
    }

    //대댓글 등록
    @PostMapping("/comment/{parentCommentId}/reply")
    public ResponseEntity<CommentResponseDto> createReplyToComment(
            @PathVariable Long postId,
            @PathVariable ObjectId parentCommentId,
            @RequestBody @Valid CommentRequestDto request
    ) {
        Long memberId = 1L; // 테스트용 하드코딩
        String writer = request.getWriter();

        CommentResponseDto response = commentService.createReplyToComment(postId, parentCommentId, memberId, writer, request);
        return ResponseEntity.ok(response);
    }

    //댓글+ 대댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<CommentsResponseDto> getCommentsByPostId(@PathVariable Long postId) {
        CommentsResponseDto response = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(response);
    }

    //댓글 수정
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long postId,
            @PathVariable String commentId,
            @RequestBody @Valid CommentRequestDto request
    ){
        Long memberId=1L; //로그인 사용자 대체
        CommentResponseDto response = commentService.updateComment(postId,commentId,memberId,request);
        return ResponseEntity.ok(response);
    }

    //댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<DeleteResponseDto> deleteComment(
            @PathVariable Long postId,
            @PathVariable("commentId") ObjectId commentId,
            @RequestParam Long memberId
    ) {
        DeleteResponseDto response = commentService.deleteComment(commentId, memberId);
        return ResponseEntity.ok(response);
    }
}