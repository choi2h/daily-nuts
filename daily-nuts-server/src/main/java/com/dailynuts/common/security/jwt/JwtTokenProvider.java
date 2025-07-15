package com.dailynuts.common.security.jwt;

import com.dailynuts.member.dto.MemberLoginRequestDto;
import com.dailynuts.member.dto.MemberLoginResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    public String provide(MemberLoginRequestDto req) {
        // 시크릿 키 생성 메서드
        SecretKey secretKey = Keys.hmacShaKeyFor("NoNutsGang!RejectNuts2024$@^#LongSecretKeyHere!!".getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject(req.getLoginId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }
}
