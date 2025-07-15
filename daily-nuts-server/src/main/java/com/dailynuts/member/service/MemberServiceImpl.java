package com.dailynuts.member.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.common.security.jwt.JwtTokenProvider;
import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberLoginResponseDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Long createMember(MemberSignupRequestDto req) {
        Member member = memberRepository.save(createHashedMember(req));

        System.out.println(member.getLoginId() + " = " + member.getPassword());

        return member.getId();
    }

    @Override
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto req) {
        Member member = memberRepository.findByLoginId(req.getLoginId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        System.out.println("1 = " + req.getLoginId() + " " + member.getLoginId() + "|" + req.getPassword() + " " + member.getPassword());

        if (passwordEncoder.matches(req.getPassword(), member.getPassword())){

            return jwtTokenProvider.provide(req);
        } else {
           throw new CustomException(CustomErrorCode.PASSWORD_DOSE_NOT_MATCH);
        }

    }

    private Member createHashedMember(MemberSignupRequestDto req) {
        Member member = memberMapper.toSignupEntity(req);
        String hashPassword = passwordEncoder.encode(member.getPassword());
        member = memberMapper.toHashEntity(member, hashPassword);

        return member;
    }

}