package com.dailynuts.member.service;

import com.dailynuts.member.dto.MemberRequestDto;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.formatter.MemberFormatter;
import com.dailynuts.member.service.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MemberFormatter memberFormatter;

    @Override
    public Long createMember(MemberRequestDto Req) {
        Member member = memberMapper.getMember(Req);
        member = memberRepository.save(memberFormatter.hashPassword(member));
        System.out.println(member.getPassword());
        return member.getId();
    }
}
