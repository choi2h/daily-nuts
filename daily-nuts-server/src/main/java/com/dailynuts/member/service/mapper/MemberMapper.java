package com.dailynuts.member.service.mapper;

import com.dailynuts.member.dto.MemberMyPageResponseDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.entity.Member;
import com.dailynuts.security.jwt.JwtMember;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toHashEntity(MemberSignupRequestDto req, String hashPassword) {

        return Member.builder()
                .loginId(req.getLoginId())
                .name(req.getName())
                .password(hashPassword)
                .phoneNumber(req.getPhoneNumber())
                .email(req.getEmail())
                .birth(req.getBirth())
                .build();
    }

    /**
     * JWT로 인증된 회원 정보를 바탕으로
     * 마이페이지 화면에 필요한 필드 값을 빌드 합니다.
     *
     * @param jwtMember 스프링 시큐리티의 @AuthenticationPrincipal로 주입된
     *                   인증된 회원 정보(JwtMember)
     * @return 마이페이지 렌더링에 사용될
     *         MemberMyPageResponseDto 객체
     */
    public MemberMyPageResponseDto convertToMemberMyPageResponse(JwtMember jwtMember) {

        return MemberMyPageResponseDto.builder()
                .name(jwtMember.getName())
                .loginId(jwtMember.getLoginId())
                .email(jwtMember.getEmail())
                .birth(jwtMember.getBirth())
                .phone(formatKoreanMobile(jwtMember.getPhoneNumber()))
                .updatedAt(jwtMember.getUpdatedAt().toLocalDate())
                .build();
    }


    /**
     * 11자의 핸드폰 번호를 한국식 전화번호 포맷으로 변경합니다.
     * ex) 01012345789 => 010-1234-5678
     *
     * @param phoneNumber 11자의 숫자로 이루어진 번호 변수
     * @return 한국식 전화번호 포맷의 String값
     */
    private String formatKoreanMobile (String phoneNumber){
        StringBuilder sb = new StringBuilder(phoneNumber);
        sb.insert(3, '-');
        sb.insert(8, '-');

        return sb.toString();
    }
}