package com.dailynuts.member.service;

import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;

public interface MemberService {
    Long createMember(MemberSignupRequestDto memberSignupRequestDto);
    String[] loginMember(MemberLoginRequestDto memberLoginRequestDto);
    String[] refreshToken(String refreshToken);
}