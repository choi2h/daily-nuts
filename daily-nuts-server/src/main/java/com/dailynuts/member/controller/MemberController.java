package com.dailynuts.member.controller;

import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.service.MemberService;
import com.dailynuts.security.jwt.JwtMember;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/api/my-profile") // 인증된 사용자 정보 확인용 엔드포인트
    public String getAuthenticatedMemberProfile(@AuthenticationPrincipal JwtMember jwtMember) {
        // JwtMember 객체를 통해 인증된 사용자 정보에 접근합니다.
        // JwtMember 클래스에 정의된 게터(getId, getLoginId, getName, getRole 등)를 사용합니다.
        System.out.println("인증된 사용자 ID: " + jwtMember.getId());
        System.out.println("인증된 사용자 로그인 ID: " + jwtMember.getLoginId());
        System.out.println("인증된 사용자 이름: " + jwtMember.getName());
        System.out.println("인증된 사용자 역할: " + jwtMember.getRole());

        return "ert";
    }
}