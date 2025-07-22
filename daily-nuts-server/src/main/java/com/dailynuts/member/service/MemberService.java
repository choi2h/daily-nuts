package com.dailynuts.member.service;

import com.dailynuts.member.dto.*;
import com.dailynuts.security.jwt.JwtMember;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    Long createMember(MemberSignupRequestDto memberSignupRequestDto);
    MemberLoginResponseDto loginMember(MemberLoginRequestDto memberLoginRequestDto);
    String[] refreshToken(String refreshToken);
    boolean existsByLoginId(String loginId);
    MemberMyPageResponseDto getMemberInfo(JwtMember jwtMember);
    MemberMyPageResponseDto updateMember(MemberEditRequestDto req, MultipartFile file, JwtMember jwtMember);
    MemberDetailInfoResponseDto getExpertMemberInfo(Long targetMemberId, Long requestMemberId);
}