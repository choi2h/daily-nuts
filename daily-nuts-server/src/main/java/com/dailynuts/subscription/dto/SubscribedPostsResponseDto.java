package com.dailynuts.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SubscribedPostsResponseDto {
    private List<SubscribedPostResponseDto> posts;
    private int totalPages;
    private int currentPage;
}
