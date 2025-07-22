package com.dailynuts.member.dto;

import com.dailynuts.post.dto.PostResponseDto;
import com.dailynuts.post.dto.PostTitleResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ExpertProfileResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long subscriberCount;
    private boolean isSubscribed;
    private List<PostResponseDto> fixedPosts;
    private List<PostTitleResponseDto> normalPosts;
}
