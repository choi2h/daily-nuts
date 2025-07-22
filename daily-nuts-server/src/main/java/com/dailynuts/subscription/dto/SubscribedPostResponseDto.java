package com.dailynuts.subscription.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubscribedPostResponseDto {
    private Long postId;
    private String title;
    private String contents;
    private int likeCount;
    private boolean isLiked;
    private int commentCount;
    private String writer;
}
