package com.dailynuts.member.service.mapper;

import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toSignupEntity(MemberSignupRequestDto req){

        return Member.builder()
                .loginId(req.getLoginId())
                .name(req.getName())
                .password(req.getPassword())
                .phoneNumber(req.getPhoneNumber())
                .email(req.getEmail())
                .birth(req.getBirth())
                .role(req.getRole())
                .build();
    }

    public Member toHashEntity(Member member, String hashPassword) {

        return Member.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .password(hashPassword)
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .birth(member.getBirth())
                .role(member.getRole())
                .build();
    }
}