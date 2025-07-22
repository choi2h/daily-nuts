package com.dailynuts.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder @Getter
public class SubscribeExpertResponseDto {
    private Long id;
    private String name;
    @Setter
    private String profileImage;
    private int postCount;
    private LocalDate subscribeDate;
}
