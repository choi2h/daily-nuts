package com.dailynuts.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostTitleResponseDto {
    private Long id;
    private String title;
    private String writer;
    private int likeCount;
    private boolean liked;
    private LocalDateTime createdAt;
}