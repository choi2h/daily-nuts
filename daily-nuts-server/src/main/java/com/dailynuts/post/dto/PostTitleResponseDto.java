package com.dailynuts.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostTitleResponseDto {
    private Long id;
    private String title;
    private String writer;
    private int likeCount;
}
