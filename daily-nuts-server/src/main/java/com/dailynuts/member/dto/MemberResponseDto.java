package com.dailynuts.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MemberResponseDto {
    private String loginId;
    private String name;
}
