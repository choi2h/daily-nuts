package com.dailynuts.security.jwt;

import com.dailynuts.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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

        String token = null;

        Cookie[] cookies = req.getCookies();
        if (cookies == null || cookies.length == 0) {
            filterChain.doFilter(req, res);
            return;
        }

        for (Cookie cookie : cookies){
            if("refreshToken".equals(cookie.getName())){
                token = cookie.getValue();
                break;
            }
        }

        if (token == null) {
            filterChain.doFilter(req,res);
            return;
        }

        if (!jwtUtils.validateToken(token)) {
            filterChain.doFilter(req,res);
            return;
        }

        res.addHeader(HttpHeaders.SET_COOKIE,
                jwtService.tokenRefresh(token).toString());

        log.info("리프레시 토큰으로 새 액서스 토큰 발급 : {}", token);

        String loginId = jwtUtils.getLoginIdFromToken(token);

        // 시큐리티 컨텍스트에 저장하기 위한 authentication 객체 생성
        if (loginId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.cookByLoginId(loginId);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(req, res);

    }
}