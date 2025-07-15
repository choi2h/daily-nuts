package com.dailynuts.member.controller;

import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberSignupRequestDto req){

        memberService.createMember(req);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginMember(@RequestBody @Valid MemberLoginRequestDto req){

        ResponseCookie cookie = memberService.loginMember(req);

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .build();
    }

}