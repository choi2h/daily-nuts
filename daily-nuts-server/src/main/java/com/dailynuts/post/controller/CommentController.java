package com.dailynuts.post.controller;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.post.dto.CommentRequestDto;
import com.dailynuts.post.dto.CommentResponseDto;
import com.dailynuts.post.dto.CommentsResponseDto;
import com.dailynuts.post.dto.DeleteResponseDto;
import com.dailynuts.post.repository.PostRepository;
import com.dailynuts.post.service.CommentService;
import com.dailynuts.security.jwt.JwtMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}")
public class CommentController {
    private final CommentService commentService;
    private final PostRepository postRepository;

    //댓글 등록
    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentRequestDto request,
            @AuthenticationPrincipal JwtMember jwtMember
    ) {
        if (!postRepository.existsById(postId)) {
            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
        }
        Long memberId = jwtMember.getId();
        String writer = jwtMember.getName();

        CommentResponseDto response = commentService.createComment(postId, memberId, writer, request);
        return ResponseEntity.ok(response);
    }

    //대댓글 등록
    @PostMapping("/comment/{parentCommentId}/reply")
    public ResponseEntity<CommentResponseDto> createReplyToComment(
            @PathVariable Long postId,
            @PathVariable ObjectId parentCommentId,
            @AuthenticationPrincipal JwtMember jwtMember,
            @RequestBody @Valid CommentRequestDto request
    ) {
        Long memberId = jwtMember.getId();
        String writer = jwtMember.getName();

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
            @AuthenticationPrincipal JwtMember jwtMember,
            @RequestBody @Valid CommentRequestDto request
    ){
        Long memberId = jwtMember.getId();
        CommentResponseDto response = commentService.updateComment(postId,commentId,memberId,request);
        return ResponseEntity.ok(response);
    }

    //댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<DeleteResponseDto> deleteComment(
            @PathVariable("commentId") ObjectId commentId,
            @AuthenticationPrincipal JwtMember jwtMember
    ) {
        Long memberId = jwtMember.getId();
        DeleteResponseDto response = commentService.deleteComment(commentId, memberId);
        return ResponseEntity.ok(response);
    }
}