package com.dailynuts.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberEditRequestDto {

    @NotNull
    @NotBlank
    private String birth;

    @NotNull
    @NotBlank
    private String phoneNumber;

    @NotNull
    @NotBlank
    private String email;
}
