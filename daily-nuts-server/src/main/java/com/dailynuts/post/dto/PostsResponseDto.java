package com.dailynuts.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostsResponseDto {
    private final List<PostResponseDto> posts;
    private final int totalPage;
    private final int currentPage;
}
