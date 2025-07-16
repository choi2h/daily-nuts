package com.dailynuts.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String writer;
    private String categoryName;
    private Long categoryId;
    private int likeCount;
    private boolean isPinned;
    private LocalDateTime createdAt;
}
