package com.dailynuts.member.dto;

import com.dailynuts.member.entity.type.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Builder
@AllArgsConstructor
public class MemberLoginResponseDto {

    private String loginId;

    private String name;

    private Role role;

    private Long memberId;

    @Setter
    private String profileImageName;

    @JsonIgnore
    private String accessToken;

    @JsonIgnore
    private String refreshToken;
}