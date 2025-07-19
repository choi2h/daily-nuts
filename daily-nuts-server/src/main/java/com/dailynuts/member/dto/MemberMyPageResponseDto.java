package com.dailynuts.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 회원의 마이페이지 렌더링에 필요한 데이터를 담는 DTO 클래스.
 *
 * 이 클래스는 mypage의 일반 회원 정보의 렌더링에서만 사용되며,
 * 필드 값은 실제 페이지에서 출력되는 형태로 변환된 값들을 담는다.
 **/
@Getter
@Builder
@AllArgsConstructor
public class MemberMyPageResponseDto {

    @NotBlank @NotNull
    private String name;

    @NotBlank @NotNull
    private String loginId;

    @NotBlank @NotNull
    private LocalDate birth;

    @NotBlank @NotNull
    private String phoneNumber;

    @NotBlank @NotNull
    private String email;

    @NotBlank @NotNull
    private LocalDate updatedAt;
}
