package com.dailynuts.member.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.dto.*;
import com.dailynuts.security.jwt.JwtMember;
import com.dailynuts.security.jwt.JwtUtils;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// MemberController와 비즈니스 로직이 연결된
// 하나밖에 없는 Service
// 컨트롤러의 모든 로직은 여기서 나온다.
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // 멤버 아이디를 db에 저장
    @Override
    public Long createMember(MemberSignupRequestDto req) {
        Member member = memberRepository.save(createHashedMember(req));

        // 회원가입 성공시 어떤 데이터 보낼건지 정해야함 (*수정예정*)
        return member.getId();
    }

    // 멤버 아이디를 db와 비교
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

    // 토큰을 생성 (리프레시용)
    public String[] refreshToken(String refreshToken) {
        String[] tokens = new String[2];

        if (jwtUtils.validateToken(refreshToken)) {
            String loginId = jwtUtils.getLoginIdFromToken(refreshToken);
            tokens[0] = jwtUtils.provideToken(loginId);
            tokens[1] = jwtUtils.provideRefreshToken(loginId);
        } else {
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VALID);
        }

        return tokens;
    }

    // 로그인 아이디를 db와 비교
    @Override
    @Transactional(readOnly = true)
    public boolean existsByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    /**
     * JWT로 인증된 회원 정보를 바탕으로
     * 마이페이지 화면에 필요한 DTO를 생성합니다.
     *
     * @param jwtMember 스프링 시큐리티의 @AuthenticationPrincipal로 주입된
     *                  인증된 회원 정보(JwtMember)
     * @return 마이페이지 렌더링에 사용될
     * MemberMyPageResponseDto 객체
     */
    @Override
    public MemberMyPageResponseDto buildMyPage(JwtMember jwtMember) {

        return memberMapper.convertToMemberMyPageResponse(jwtMember);
    }

    @Override
    @Transactional
    public void updateMember(MemberEditRequestDto req, JwtMember jwtMember) {
        Member member = memberRepository.findByLoginId(jwtMember.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birth = LocalDate.parse(req.getBirth(), fmt);

        member.setPhoneNumber(req.getPhoneNumber());
        member.setBirth(birth);
        member.setEmail(req.getEmail());
    }

    // 비밀번호 해시화 로직
    private Member createHashedMember(MemberSignupRequestDto req) {

        // 비밀번호 해시화
        String hashPassword = passwordEncoder.encode(req.getPassword());

        // 해시화 Entity 생성
        return memberMapper.toHashEntity(req, hashPassword);
    }

}