package com.dailynuts.security.jwt;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        String accessToken = null;

        // 헤더에서 토큰 파싱
        if (header != null && header.startsWith("Bearer ")) {
            accessToken = header.substring(7);
        } else {
            filterChain.doFilter(req, res);
        }

        // 토큰 검증 jwtUtils.validateToken(accessToken)
        // 검증 통과되면
        if (accessToken != null && jwtUtils.validateToken(accessToken)) {
            String loginId = jwtUtils.getLoginIdFromToken(accessToken);

            // 시큐리티 컨텍스트에 인증 객체 담기
            UserDetails userDetails = jwtService.cookByLoginId(loginId);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(req, res);
    }

}