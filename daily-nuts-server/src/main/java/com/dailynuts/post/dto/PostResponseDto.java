package com.dailynuts.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String categoryName;
    private int likeCount;
    private boolean isPinned;
    private LocalDateTime createdAt;
}
