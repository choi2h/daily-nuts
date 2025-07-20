package com.dailynuts.member.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.dto.MemberLoginResponseDto;
import com.dailynuts.security.jwt.JwtUtils;
import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto req) {
        // 아이디 존재 확인
        Member member = memberRepository.findByLoginId(req.getLoginId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        // 패스워드 일치 확인
        if (!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_DOSE_NOT_MATCH);
        }
        String accessToken = jwtUtils.provideToken(member.getLoginId());
        String refreshToken = jwtUtils.provideRefreshToken(member.getLoginId());
      
        return MemberLoginResponseDto.builder()
             .loginId(member.getLoginId())
             .name(member.getName())
             .role(member.getRole())
             .accessToken(accessToken)
             .refreshToken(refreshToken)
             .memberId(member.getId())
             .build();
    }

    public String[] refreshToken(String refreshToken) {
        String[] tokens = new String[2];

        if(jwtUtils.validateToken(refreshToken)) {
            String loginId = jwtUtils.getLoginIdFromToken(refreshToken);
            tokens[0] = jwtUtils.provideToken(loginId);
            tokens[1] = jwtUtils.provideRefreshToken(loginId);
        } else {
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VALID);
        }
      
        return tokens;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    private Member createHashedMember(MemberSignupRequestDto req) {

        // 비밀번호 해시화
        String hashPassword = passwordEncoder.encode(req.getPassword());

        // 해시화 Entity 생성
        return memberMapper.toHashEntity(req, hashPassword);
    }

}