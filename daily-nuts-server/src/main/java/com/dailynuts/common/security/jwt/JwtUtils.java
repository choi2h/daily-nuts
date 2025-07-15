package com.dailynuts.common.security.jwt;

import com.dailynuts.member.dto.MemberLoginRequestDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    // 토큰 생성 메서드
    public String provideToken(MemberLoginRequestDto req) {
        // 시크릿 키 생성
        SecretKey secretKey = Keys.hmacShaKeyFor("NoNutsGang!RejectNuts2024$@^#givememoney".getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject(req.getLoginId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    // 쿠키 생성 메서드
    public ResponseCookie tokenLovesCookie(String token) {
        return ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60)
                .sameSite("Strict")
                .build();
    }
}
