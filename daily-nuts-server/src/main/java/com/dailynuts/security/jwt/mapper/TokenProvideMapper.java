package com.dailynuts.security.jwt.mapper;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * JwtUtils.TokenProvide() 전용 매퍼 클래스
 *
 * 1. 액세스 토큰을 생성한다.
 * 2. 리프레시 토큰을 생성한다.
 */
@Component
@Slf4j
public class TokenProvideMapper {
    /// 액세스 토큰 만료시간
    @Value("${jwt.expiration.access-token}")
    private long accessTokenSeconds;

    /// 리프레시 토큰 만료시간
    @Value("${jwt.expiration.refresh-token}")
    private long refreshTokenSeconds;

    /**
     * 액세스 토큰을 생성하는 메서드
     *
     * @param loginId 외부 클라이언트의 요청으로 들어온 loginId 값
     * @return 액세스 인증용으로 사용하는 Jwt 토큰을 발급한다.
     */
    public String loginIdToAccessToken(String loginId, SecretKey key) {
        // jti로 사용하기 위한 랜덤 수 생성
        String jti = UUID.randomUUID().toString();
        Date now = Date.from(Instant.now());
        Date accessExpiry = Date.from(Instant.now().plusSeconds(accessTokenSeconds));

        return Jwts.builder()
                .id(jti)
                .subject(loginId)
                .issuedAt(now)
                .expiration(accessExpiry)
                .signWith(key)
                .compact();
    }

    /**
     * 리프레시 토큰을 생성하는 메서드
     *
     * @param loginId 외부 클라이언트의 요청으로 들어온 loginId 값
     * @return 리프레시 인증용으로 사용하는 Jwt 토큰을 발급한다.
     */
    public String loginIdToRefreshToken(String loginId, SecretKey key) {
        // jti로 사용하기 위한 랜덤 수 생성
        String jti = UUID.randomUUID().toString();
        Date now = Date.from(Instant.now());
        Date refreshExpiry = Date.from(Instant.now().plusSeconds(refreshTokenSeconds));

        return Jwts.builder()
                .id(jti)
                .subject(loginId)
                .issuedAt(now)
                .expiration(refreshExpiry)
                .signWith(key)
                .compact();
    }

}
