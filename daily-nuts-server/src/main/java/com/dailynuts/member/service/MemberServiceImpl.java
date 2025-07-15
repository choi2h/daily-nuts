package com.dailynuts.member.service;

import com.dailynuts.member.dto.MemberRequestDto;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Long createMember(MemberRequestDto req) {
        Member member = memberRepository.save(memberMapper.convertMember(req));
        System.out.println(member.getLoginId() + " = " + member.getPassword());
        return member.getId();
    }
}
