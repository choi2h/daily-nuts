package com.dailynuts.member.service;

import com.dailynuts.member.dto.MemberRequestDto;

public interface MemberService {
    Long createMember(MemberRequestDto memberRequestDto);

}
