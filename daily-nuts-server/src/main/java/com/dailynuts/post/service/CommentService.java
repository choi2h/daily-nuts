package com.dailynuts.post.service;

import com.dailynuts.post.dto.CommentRequestDto;
import com.dailynuts.post.dto.CommentResponseDto;
import com.dailynuts.post.dto.CommentsResponseDto;
import com.dailynuts.post.dto.DeleteResponseDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    //댓글 등록
    CommentResponseDto createComment(Long postId, Long memberId, String writer, CommentRequestDto request);

    //대댓글
    CommentResponseDto createReplyToComment(Long postId, ObjectId parentCommentId, Long memberId, String writer, CommentRequestDto request);

    //댓글 조회
    CommentsResponseDto getCommentsByPostId(Long postId);

    //댓글 수정
    CommentResponseDto updateComment(Long postId, String commentId, Long memberId, CommentRequestDto request);

    //댓글 삭제
    DeleteResponseDto deleteComment(ObjectId commentId, Long memberId);
}
