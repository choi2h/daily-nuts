package com.dailynuts.security.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.security.jwt.JwtMember;
import com.dailynuts.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;

    public UserDetails cookByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                                        .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        return new JwtMember(member);
    }

    @Override
    public ResponseCookie tokenRefresh(String refreshToken) {

        String loginId = jwtUtils.getLoginIdFromToken(refreshToken);

        // 토큰 생성
        String accessToken = jwtUtils.provideToken(loginId);

        return jwtUtils.provideCookie(accessToken);
    }


}