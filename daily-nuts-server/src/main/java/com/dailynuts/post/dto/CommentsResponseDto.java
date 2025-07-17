package com.dailynuts.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class CommentsResponseDto {
    private List<CommentResponseDto> comments;
    public CommentsResponseDto() {
        comments = new ArrayList<>();
    }

    public CommentsResponseDto(List<CommentResponseDto> comments) {
        this.comments = comments;
    }

    public void addComment(CommentResponseDto comment) {
        comments.add(comment);
    }
}
