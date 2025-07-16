package com.dailynuts.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    @NotNull
    private Long categoryId;
}
