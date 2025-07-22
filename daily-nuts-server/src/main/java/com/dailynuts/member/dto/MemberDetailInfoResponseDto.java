package com.dailynuts.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class MemberDetailInfoResponseDto {
    private Long memberId;
    private String name;
    private String description;
    @Setter
    private String profileImage;
    private Long subscriberCount;
    private boolean isSubscribed;
}
