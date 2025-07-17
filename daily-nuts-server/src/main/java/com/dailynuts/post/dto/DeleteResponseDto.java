package com.dailynuts.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DeleteResponseDto {
    private String message;
    private String deletedCommentId;

    public DeleteResponseDto(String message, String deletedCommentId) {
        this.message = message;
        this.deletedCommentId = deletedCommentId;
    }
}
