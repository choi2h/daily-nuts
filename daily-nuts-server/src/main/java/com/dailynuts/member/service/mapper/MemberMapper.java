package com.dailynuts.member.service.mapper;

import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toHashEntity(MemberSignupRequestDto req, String hashPassword) {

        return Member.builder()
                .loginId(req.getLoginId())
                .name(req.getName())
                .password(hashPassword)
                .phoneNumber(req.getPhoneNumber())
                .email(req.getEmail())
                .birth(req.getBirth())
                .role(req.getRole())
                .build();
    }
}