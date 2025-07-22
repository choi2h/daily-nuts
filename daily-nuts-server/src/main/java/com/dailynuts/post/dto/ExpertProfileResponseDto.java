package com.dailynuts.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ExpertProfileResponseDto {
    private Long id;
    private String name;
    @Setter
    private String profileImage;
    private String description;
    private Long subscriberCount;
    private boolean isSubscribed;
    private List<PostResponseDto> fixedPosts;
    private List<PostTitleResponseDto> normalPosts;
}
