package com.dailynuts.post.service;

import com.dailynuts.post.dto.CommentRequest;
import com.dailynuts.post.dto.CommentResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface CommentService {
    //댓글 등록
    CommentResponse createComment(Long postId, Long memberId, String writer, CommentRequest request);

    //대댓글
    CommentResponse createReplyToComment(Long postId, ObjectId parentCommentId, Long memberId, String writer, CommentRequest request);

    //댓글 조회
    List<CommentResponse> getCommentsByPostId(Long postId);

    //댓글 수정
    CommentResponse updateComment(Long postId, String commentId, Long memberId, CommentRequest request);
}
