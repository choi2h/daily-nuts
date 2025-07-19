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

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // JWT 검사에서 제외할 public endpoint 목록
        String path = request.getServletPath();
        return  path.equals("/member/signup") ||
                path.equals("/member/login")  ||
                path.equals("/member/refresh")||
                path.equals("/member/exist");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JWT Authentication Filter start.");
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = null;

        // 헤더에서 토큰 파싱
        if (header != null && header.startsWith("Bearer ")) {
            accessToken = header.substring(7);
        } else {
            log.debug("Not exist token in header. header: {}", header);
            filterChain.doFilter(req, res);
            return;
        }

        // 토큰 검증 jwtUtils.validateToken(accessToken)
        // 검증 통과되면
        log.debug("Receive accessToken: {}", accessToken);
        if (jwtUtils.validateToken(accessToken)) {
            String loginId = jwtUtils.getLoginIdFromToken(accessToken);

            // 시큐리티 컨텍스트에 인증 객체 담기
            UserDetails userDetails = jwtService.cookByLoginId(loginId);
            log.debug("Find username by loginId from token: {}", userDetails.getUsername());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        log.debug("JWT Authentication Filter end.");
        filterChain.doFilter(req, res);
    }

}