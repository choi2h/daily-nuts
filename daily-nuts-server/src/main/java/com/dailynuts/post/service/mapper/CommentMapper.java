package com.dailynuts.post.service.mapper;

import com.dailynuts.post.dto.CommentRequest;
import com.dailynuts.post.dto.CommentResponse;
import com.dailynuts.post.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    public Comment toEntity(CommentRequest request, Long postId, Long memberId, String writer, String parentCommentId){
        return Comment.builder()
                .postId(postId)
                .memberId(memberId)
                .writer(writer)
                .parentCommentId(parentCommentId)
                .contents(request.getContents())
                .build();
    }

    public CommentResponse toResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .memberId(comment.getMemberId())
                .writer(comment.getWriter())
                .contents(comment.getContents())
                .createdAt(comment.getCreatedAt())
                .replies(List.of())
                .build();
    }

    public List<CommentResponse> toResponseList(List<Comment> comments) {
        return comments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
