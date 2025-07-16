package com.dailynuts.post.service;

import com.dailynuts.post.dto.CommentRequest;
import com.dailynuts.post.dto.CommentResponse;

import java.util.List;

public interface CommentService {
    //댓글 등록
    CommentResponse createComment(Long postId, Long memberId, String writer, CommentRequest request);

    //대댓글
    CommentResponse createReplyToComment(Long postId, String parentCommentId, Long memberId, String writer, CommentRequest request);

    //조회
    List<CommentResponse> getCommentsByPostId(Long postId);
}
