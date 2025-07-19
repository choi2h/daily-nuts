package com.dailynuts.member.controller;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberLoginResponseDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.service.MemberService;
import com.dailynuts.security.jwt.JwtMember;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member")
@AllArgsConstructor @Slf4j
public class MemberController {

    private final MemberService memberService;

    // 회원가입 기능
    // 프론트에서 폼 데이터를 받아 jpa로 db에 값 저장
    @PostMapping("/signup")
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberSignupRequestDto req) {

        memberService.createMember(req);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    // 로그인 기능
    // 프론트에서 폼 데이터를 받아 jpa로 db에 값 비교
    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> loginMember(@RequestBody @Valid MemberLoginRequestDto req) {
        MemberLoginResponseDto LoginResponse = memberService.loginMember(req);

        return ResponseEntity.ok()
                             .header(HttpHeaders.AUTHORIZATION, "Bearer " + LoginResponse.getAccessToken())
                             .header("Refresh-Token", LoginResponse.getRefreshToken())
                             .body(LoginResponse);
    }

    // 아이디 중복 기능
    // 프론트에서 폼 데이터를 파라미터로 받아 jpa로 존재 여부 확인
    @GetMapping("/exist")
    public ResponseEntity<Map<String, Boolean>> isLoginIdExists(
            @RequestParam String loginId) {

        boolean exists = memberService.existsByLoginId(loginId);

        return ResponseEntity.ok(Map.of("exists", exists));
    }

    // 로그아웃 기능
    // 만료시간이 0인 토큰을 지급해서 헤더를 덮어씌우는 방식
    // 이후 정리 작업은 프론트에서 해결
    @PostMapping("logout")
    public ResponseEntity<Void> logoutMember(@AuthenticationPrincipal JwtMember jwtMember) {

        String token = memberService.logoutMember(jwtMember);

        return ResponseEntity.ok()
                             .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                             .header("Refresh-Token", token)
                             .build();
    }

    // 리프레시 토큰 지급 기능
    // 리프레시 토큰이 없으면 TOKEN_NOT_VALID를 반환
    // 리프레시 토큰과 액세스 토큰을 지급해서 헤더를 덮어씌우는 방식
    @GetMapping("/refresh")
    public ResponseEntity<Void> refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        if (refreshToken == null) {
            log.warn("리프레시 토큰이 없음");
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VALID);
        }

        String[] tokens = memberService.refreshToken(refreshToken);

        return ResponseEntity.ok()
                             .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens[0])
                             .header("Refresh-Token", tokens[1])
                             .build();
    }

}