package com.dailynuts.member.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.common.security.jwt.JwtUtils;
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

        System.out.println(member.getLoginId() + " = " + member.getPassword());

        // 회원가입 성공시 어떤 데이터 보낼건지 정해야함 (*수정예정*)
        return member.getId();
    }

    @Override
    public ResponseCookie loginMember(MemberLoginRequestDto req) {

        return processLoginAndGenerateCookie(req);
    }

    private ResponseCookie processLoginAndGenerateCookie(MemberLoginRequestDto req) {
        // 아이디 존재 확인
        Member member = memberRepository.findByLoginId(req.getLoginId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        // 패스워드 일치 확인
        if (!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_DOSE_NOT_MATCH);
        }

        // 토큰 생성
        String token = jwtUtils.provideToken(req);

        // 쿠키 생성
        ResponseCookie cookie = jwtUtils.tokenLovesCookie(token);

        return cookie;
    }

    private Member createHashedMember(MemberSignupRequestDto req) {
        // 회원가입용 엔티티 생성
        Member member = memberMapper.toSignupEntity(req);

        // 회원가입용 엔티티에서 비밀번호 꺼내고 해시화
        String hashPassword = passwordEncoder.encode(member.getPassword());

        // 회원가입용 엔티티에 해시화된 비밀번호 주입
        member = memberMapper.toHashEntity(member, hashPassword);

        return member;
    }

}