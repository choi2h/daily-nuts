package com.dailynuts.member.controller;

import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberLoginResponseDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> createMember(@RequestBody @Valid MemberSignupRequestDto req){

        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(req));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> loginMember(@RequestBody @Valid MemberLoginRequestDto req){

        return ResponseEntity.ok(memberService.loginMember(req));
    }

}