package com.dailynuts.security.jwt;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.security.entity.type.TokenType;
import com.dailynuts.security.jwt.mapper.TokenProvideMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


/**
 * Jwt토큰의 생성, 검증, 파싱하여 데이터 생성과 추출을 담당하는 클래스
 *
 * 1. Jwt를 제공한다.
 * 2. Jwt를 검증한다.
 * 3. Jwt토큰을 파싱하여 Subject(loginId)를 읽어온다.
 **/
@Component @Slf4j
@RequiredArgsConstructor
public class JwtUtils {
    /// 토큰 생성을 담당하는 클래스
    private final TokenProvideMapper mapper;

    /// 시크릿 키의 원본이 되는 String
    @Value("${jwt.secret}")
    private String secretKeyString;

    /// HMAC-SHA256 서명용 키 객체
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        try {
            this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            log.error("TokenProvideMapper의 Jwt Secret Key 초기화 실패", e);
            throw new CustomException(CustomErrorCode.SECRET_KEY_INITIALIZATION_FAILED);
        }
    }

    /**
     * 1. 토큰을 발급하는 메서드
     *
     * 매개변수를 받아 알맞는 타입의 토큰을 생성하여 문자열의 토큰을 리턴합니다.
     * @param loginId 외부 클라이언트로부터 받은 loginId 데이터
     * @param tokenType 액세스 혹은 리프레시 중 하나를 선택하는 구별용 변수
     * @return Jwt문자열
     */
    public String provideToken(String loginId, TokenType tokenType) {
        String token = null;

        // 액세스 토큰 생성
        if(tokenType == TokenType.ACCESS){
            token = mapper.loginIdToAccessToken(loginId, secretKey);
        }

        // 리프레시 토큰 생성
        if(tokenType == TokenType.REFRESH){
            token = mapper.loginIdToRefreshToken(loginId, secretKey);
        }

        // 액세스와 리프레시 토큰을 생성하지 못했을 경우
        // TOKEN_CREATION_FAILED 예외를 여기서 던집니다
        if (token == null){
            log.warn("provideToken에서 토큰 생성에 실패했습니다. loginId={}, tokenType={}",
                                      loginId, tokenType);
            throw new CustomException(CustomErrorCode.TOKEN_CREATION_FAILED);
        }

        return token;
    }

    /**
     * 2. 토큰의 유효성을 검증하는 메서드
     *
     * 토큰의 서명이 위조됐는지 비교검사하고 만료시간이 지났는지 검사하여 boolean으로 리턴합니다.
     * @param token jwt를 받습니다.
     * @return true or false
     */
    public boolean validateToken(String token) {
        try {
            // 서명 검증과 만료 시간 검사를 자동으로 해줍니다
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;

        } catch (SignatureException e) {
            log.warn("JWT 서명 오류");
        } catch (MalformedJwtException e) {
            log.warn("JWT 구조 오류");
        } catch (ExpiredJwtException e) { // 토큰 만료
            log.warn("토큰 만료");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 클레임 문자열 비어있음");
        }

        return false;
    }

    /**
     * 3. 토큰을 파싱하여 Subject를 추출하는 메서드
     *
     * 토큰의 서명 검사 / 만료시간 검사 / 구조 유효성 검사 이후 token의 subject를 추출합니다.
     * @param token jwt를 받습니다.
     * @return 토큰을 만들 때 입력했던 Subject값을 반환합니다.
     */
    public String getLoginIdFromToken(String token) {
        try {

            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            log.warn("getLoginIdFromToken 메서드에서 토큰의 유효성 검증 실패");
            throw new CustomException(CustomErrorCode.TOKEN_VALIDATION_FAILED);
        }
    }
}