package com.dailynuts.security.controller;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.security.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
@AllArgsConstructor
public class SecurityController {

    private final JwtService jwtService;

    @PostMapping("/refreshToken")
    public ResponseEntity<Void> tokenRefresh(@CookieValue(name = "refreshToken", required = false) String refreshToken){

        if (refreshToken == null){
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VALID);
        }

        ResponseCookie accessCookie = jwtService.tokenRefresh(refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .build();
    }
}
