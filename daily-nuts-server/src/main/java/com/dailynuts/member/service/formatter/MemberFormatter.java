package com.dailynuts.member.service.formatter;

import com.dailynuts.member.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@AllArgsConstructor
public class MemberFormatter {

    private final PasswordEncoder passwordEncoder;

    // 비밀번호 해시화 Formatter
    // setter 만들기 싫어서 새 member 객채를 반환
    // 비밀번호 해시화해서 똑같은 객체로 넘겨줌.
    public Member hashPassword(Member member) {
        return Member.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .password(passwordEncoder.encode(member.getPassword()))
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .birth(member.getBirth())
                .role(member.getRole())
                .build();

    }
}
