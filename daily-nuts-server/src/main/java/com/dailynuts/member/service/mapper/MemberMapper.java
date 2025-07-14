package com.dailynuts.member.service.mapper;

import com.dailynuts.member.dto.MemberRequestDto;
import com.dailynuts.member.dto.MemberResponseDto;
import com.dailynuts.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberResponseDto getResponse(Member member) {
        return MemberResponseDto.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .build();
    }

    public Member getMember(MemberRequestDto req){
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

}
