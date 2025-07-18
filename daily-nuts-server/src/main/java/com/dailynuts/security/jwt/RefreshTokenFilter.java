package com.dailynuts.security.jwt;

import com.dailynuts.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor @Slf4j
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        // 만약 JwtAuthenticationFilter를 통해서 인증이 됐다면 그냥 통과
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(req, res);
            return;
        }

        String refreshToken = req.getHeader("Refresh-Token");

        if (refreshToken != null && jwtUtils.validateToken(refreshToken)) {

            // 토큰 생성 세트
            String loginId = jwtUtils.getLoginIdFromToken(refreshToken);
            String newAccessToken = jwtUtils.provideToken(loginId);
            String newRefreshToken = jwtUtils.provideRefreshToken(loginId);

            // 응답 헤더에 실어주기
            res.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
            res.setHeader("Refresh-Token", newRefreshToken);

            log.info("리프레시 토큰으로 새 액서스 토큰 발급 : {}", newAccessToken);

            // 시큐리티 컨텍스트에 인증 객체 담기
            UserDetails userDetails = jwtService.cookByLoginId(loginId);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(req, res);

    }
}