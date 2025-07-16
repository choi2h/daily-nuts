package com.dailynuts.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentUpdateRequestDto {
    @NotBlank
    @Size(max = 100)
    private String contents;
}