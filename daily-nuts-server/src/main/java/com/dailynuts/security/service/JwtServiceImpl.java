package com.dailynuts.security.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.security.jwt.JwtMember;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final MemberRepository memberRepository;

    @Transactional
    public UserDetails convertToUserDetails(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                                        .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        return new JwtMember(member);
    }

}