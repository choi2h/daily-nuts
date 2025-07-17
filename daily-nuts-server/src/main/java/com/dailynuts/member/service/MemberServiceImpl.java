package com.dailynuts.member.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.security.jwt.JwtUtils;
import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Long createMember(MemberSignupRequestDto req) {
        Member member = memberRepository.save(createHashedMember(req));

        // 회원가입 성공시 어떤 데이터 보낼건지 정해야함 (*수정예정*)
        return member.getId();
    }

    @Override
    public ResponseCookie[] loginMember(MemberLoginRequestDto req) {

        return processLoginAndGenerateCookie(req);
    }

    private ResponseCookie[] processLoginAndGenerateCookie(MemberLoginRequestDto req) {
        // 아이디 존재 확인
        Member member = memberRepository.findByLoginId(req.getLoginId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        // 패스워드 일치 확인
        if (!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_DOSE_NOT_MATCH);
        }

        // 토큰 생성
        String accessToken = jwtUtils.provideToken(req.getLoginId());
        String refreshToken = jwtUtils.provideRefreshToken(req);

        // 쿠키 생성
        ResponseCookie accessCookie = jwtUtils.provideCookie(accessToken);
        ResponseCookie refreshCookie = jwtUtils.provideRefreshCookie(refreshToken);

        return new ResponseCookie[]{accessCookie, refreshCookie};
    }

    private Member createHashedMember(MemberSignupRequestDto req) {

        // 비밀번호 해시화
        String hashPassword = passwordEncoder.encode(req.getPassword());

        // 해시화 Entity 생성
        return memberMapper.toHashEntity(req, hashPassword);
    }

}