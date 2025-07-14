package com.dailynuts.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long categoryId;
}
