package com.dailynuts.member.controller;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberLoginResponseDto;
import com.dailynuts.member.dto.MemberSignupRequestDto;
import com.dailynuts.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@AllArgsConstructor @Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberSignupRequestDto req) {

        memberService.createMember(req);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> loginMember(@RequestBody @Valid MemberLoginRequestDto req) {

        MemberLoginResponseDto LoginResponse = memberService.loginMember(req);

        return ResponseEntity.ok()
                             .header(HttpHeaders.AUTHORIZATION, "Bearer " + LoginResponse.getAccessToken())
                             .header("Refresh-Token", LoginResponse.getRefreshToken())
                             .body(LoginResponse);
    }

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