package com.dailynuts.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeResponseDto {
    private Long postId;
    private int likeCount;
    private boolean isLiked;
}
