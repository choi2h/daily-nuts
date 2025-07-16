package com.dailynuts.security.jwt;

import com.dailynuts.member.dto.MemberLoginRequestDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {

    // 환경 변수에서 secretKey 가져오기
    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.expiration.access-token-seconds}")
    private long accessTokenSeconds;

    @Value("${cookie.max-age.access}")
    private long accessCookieSeconds;

    private SecretKey secretKey;

    // 토큰 서명 알고리즘
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // 토큰 생성 메서드
    public String provideToken(MemberLoginRequestDto req) {

        return Jwts.builder()
                .subject(req.getLoginId())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(accessTokenSeconds)))
                .signWith(secretKey)
                .compact();
    }

    // 쿠키 생성 메서드
    public ResponseCookie provideCookie(String token) {

        return ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(accessCookieSeconds)
                .sameSite("Strict")
                .build();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;

        } catch (SignatureException e) { // JWT 서명 오류 (변조)
            System.err.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) { // JWT 구조 오류 (잘못된 형식)
            System.err.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) { // 토큰 만료
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) { // 지원되지 않는 JWT 토큰
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) { // JWT 클레임 문자열 비어있음
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }

    // 토큰 파싱해서 subject(loginId) 추출
    public String getLoginIdFromToken(String token){

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}