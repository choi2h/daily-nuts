package com.dailynuts.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.expiration.access-token}")
    private long accessTokenSeconds;

    @Value("${cookie.max-age.access}")
    private long accessCookieSeconds;

    @Value("${jwt.expiration.refresh-token}")
    private long refreshTokenSeconds;

    @Value("${cookie.max-age.refresh}")
    private long refreshCookieSeconds;

    private SecretKey secretKey;

    // 토큰 서명 알고리즘
    @PostConstruct
    public void init() {

        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // 토큰 생성 메서드
    public String provideToken(String loginId) {

        return Jwts.builder()
                .subject(loginId)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(accessTokenSeconds)))
                .signWith(secretKey)
                .compact();
    }

    // 리프레시 토큰 생성 메서드
    public String provideRefreshToken(String loginId) {

        return Jwts.builder()
                .subject(loginId)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(refreshTokenSeconds)))
                .signWith(secretKey)
                .compact();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;

        } catch (SignatureException e) {
            log.warn("JWT 서명 오류: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("JWT 구조 오류: {}", e.getMessage());
        } catch (ExpiredJwtException e) { // 토큰 만료
            log.warn("토큰 만료: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT 클레임 문자열 비어있음: {}", e.getMessage());
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