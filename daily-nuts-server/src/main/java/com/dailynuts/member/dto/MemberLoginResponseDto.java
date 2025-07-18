package com.dailynuts.member.dto;

import com.dailynuts.member.entity.type.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@AllArgsConstructor
public class MemberLoginResponseDto {

    private String loginId;

    private String name;

    private Role role;

    @JsonIgnore
    private String accessCookie;

    @JsonIgnore
    private String refreshCookie;

}