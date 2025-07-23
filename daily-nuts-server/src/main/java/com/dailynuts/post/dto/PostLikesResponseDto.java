package com.dailynuts.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostLikesResponseDto {
    private List<PostLikeResponseDto> posts;
    private int totalPages;
    private int currentPage;
}
