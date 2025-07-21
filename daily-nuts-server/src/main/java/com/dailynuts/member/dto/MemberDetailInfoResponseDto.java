package com.dailynuts.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDetailInfoResponseDto {
    private Long memberId;
    private String name;
    private String description;
    private Long subscriberCount;
    private boolean isSubscribed;
}
