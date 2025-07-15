package com.dailynuts.member.service.mapper;

import com.dailynuts.member.dto.MemberRequestDto;
import com.dailynuts.member.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MemberMapper {

    private final PasswordEncoder passwordEncoder;

    // req -> 저장용 entity
    public Member convertMember(MemberRequestDto req){
        return Member.builder()
                .loginId(req.getLoginId())
                .name(req.getName())
                .password(passwordEncoder.encode(req.getPassword()))
                .phoneNumber(req.getPhoneNumber())
                .email(req.getEmail())
                .birth(req.getBirth())
                .role(req.getRole())
                .build();
    }

}
