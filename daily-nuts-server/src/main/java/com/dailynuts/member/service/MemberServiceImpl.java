package com.dailynuts.member.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.entity.Member;
import com.dailynuts.member.repository.MemberRepository;
import com.dailynuts.member.service.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long createMember(MemberSignupRequestDto req) {
        Member member = memberRepository.save(createHashedMember(req));

        System.out.println(member.getLoginId() + " = " + member.getPassword());

        return member.getId();
    }

    @Override
    public Long loginMember(MemberLoginRequestDto req) {
        // 레포지토리에서 loginID로 DB상에 존재하는 멤버 객체를 가져옴
        Member member = memberRepository.findByLoginId(req.getLoginId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        System.out.println("1 = " + member.toString());

        if (passwordEncoder.matches(req.getPassword(), member.getPassword())){

        } else {
           throw new CustomException(CustomErrorCode.PASSWORD_DOSE_NOT_MATCH);
        }

        // 성공하면 sout으로 성공 메시지 띄우면됨.
        return 0L;
    }

    private Member createHashedMember(MemberSignupRequestDto req) {
        Member member = memberMapper.toSignupEntity(req);
        String hashPassword = passwordEncoder.encode(member.getPassword());
        member = memberMapper.toHashEntity(member, hashPassword);

        return member;
    }

}