package com.dailynuts.member.service;

import com.dailynuts.member.dto.*;
import com.dailynuts.security.jwt.JwtMember;

public interface MemberService {
    Long createMember(MemberSignupRequestDto memberSignupRequestDto);
    MemberLoginResponseDto loginMember(MemberLoginRequestDto memberLoginRequestDto);
    String[] refreshToken(String refreshToken);
    boolean existsByLoginId(String loginId);
    MemberMyPageResponseDto getMemberInfo(JwtMember jwtMember);
    void updateMember(MemberEditRequestDto req, JwtMember jwtMember);
}