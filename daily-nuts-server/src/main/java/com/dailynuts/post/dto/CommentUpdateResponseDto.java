package com.dailynuts.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateResponseDto {
    private CommentResponseDto comment;
}