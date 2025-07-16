package com.dailynuts.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginRequestDto {

    @NotNull
    @NotBlank
    @Size(min=5, max=16, message = "아이디 혹은 비밀번호를 확인해주세요")
    @Pattern(regexp = "^[a-z][a-z0-9]*$", message = "아이디 혹은 비밀번호를 확인해주세요")
    private String loginId;

    @NotNull @NotBlank
    @Size(min=6, max=12, message = "아이디 혹은 비밀번호를 확인해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).+$", message = "아이디 혹은 비밀번호를 확인해주세요")
    private String password;
}