package com.dailynuts.member.dto;

import com.dailynuts.member.entity.type.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MemberSignupRequestDto {

    @NotNull @NotBlank
    @Size(min=5, max=16, message = "사용할 수 없는 아이디 입니다")
    @Pattern(regexp = "^[a-z][a-z0-9]*$", message = "사용할 수 없는 아이디 입니다")
    private String loginId;

    @NotNull @NotBlank
    @Size(min=6, max=12, message = "사용할 수 없는 비밀번호 입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).+$", message = "영문/숫자/특수문자 하나 이상 포함해야 합니다")
    private String password;

    @NotNull @NotBlank
    @Size(min=2, max=10, message = "올바른 이름을 입력해주세요")
    @Pattern(regexp = "^[가-힣]+$", message = "올바른 이름을 입력해 주세요")
    private String name;

    @NotNull @NotBlank
    @Size(min=11, max=11, message = "올바른 전화번호를 입력해주세요")
    @Pattern(regexp = "^\\d+$", message = "올바른 핸드폰 번호를 입력해 주세요")
    private String phoneNumber;

    @Email
    @NotNull @NotBlank
    @Size(min=5, max=100, message = "올바른 이메일을 입력해주세요")
    private String email;

    @NotNull @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate birth;

}