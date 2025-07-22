package com.dailynuts.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Builder
@AllArgsConstructor
public class ExpertSearchDto {
    private Long memberId;
    private String name;
    @Setter
    private String profileImage;
    private int postCount;
    private boolean isSubscribed;
}
