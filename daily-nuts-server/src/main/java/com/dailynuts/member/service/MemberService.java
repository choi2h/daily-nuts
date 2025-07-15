package com.dailynuts.member.service;

import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import org.springframework.http.ResponseCookie;

public interface MemberService {
    Long createMember(MemberSignupRequestDto memberSignupRequestDto);
    ResponseCookie loginMember(MemberLoginRequestDto memberLoginRequestDto);
}