package com.dailynuts.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeResponseDto {
    private Long postId;
    private String title;
    private String contents;
    private String writer;
    private int likeCount;
    private boolean isLiked;

    public PostLikeResponseDto(Long postId, int likeCount, boolean isLiked) {
        this.postId = postId;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }
}
