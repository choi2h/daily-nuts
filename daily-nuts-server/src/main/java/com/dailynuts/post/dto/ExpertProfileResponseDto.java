package com.dailynuts.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ExpertProfileResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long subscriberCount;
    private boolean isSubscribed;
    private List<PostResponseDto> posts;
}
