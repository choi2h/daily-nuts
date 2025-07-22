package com.dailynuts.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@AllArgsConstructor
public class ExpertSearchDto {
    private Long memberId;
    private String name;
    private String profileImageUrl;
    private int postCount;
    private boolean isSubscribed;
}
