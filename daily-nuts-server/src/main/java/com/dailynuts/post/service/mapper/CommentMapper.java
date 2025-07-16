package com.dailynuts.post.service.mapper;

import com.dailynuts.post.dto.CommentRequestDto;
import com.dailynuts.post.dto.CommentResponseDto;
import com.dailynuts.post.dto.CommentsResponseDto;
import com.dailynuts.post.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    public Comment toEntity(CommentRequestDto request, Long postId, Long memberId, String writer, ObjectId parentCommentId){
        return Comment.builder()
                .postId(postId)
                .memberId(memberId)
                .writer(writer)
                .parentCommentId(parentCommentId)
                .contents(request.getContents())
                .build();
    }

    public CommentResponseDto toResponse(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId().toHexString())
                .postId(comment.getPostId())
                .memberId(comment.getMemberId())
                .writer(comment.getWriter())
                .contents(comment.getContents())
                .createdAt(comment.getCreatedAt())
                //.replies(List.of())
                .build();
    }

    public CommentsResponseDto toResponseList(List<CommentResponseDto> list) {
        return new CommentsResponseDto(list);
    }
}