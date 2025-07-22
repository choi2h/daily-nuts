package com.dailynuts.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder @Getter
public class SubscribeExpertResponseDto {
    private Long id;
    private String name;
    private String profileImageUrl;
    private int postCount;
    private LocalDate subscribeDate;
}
