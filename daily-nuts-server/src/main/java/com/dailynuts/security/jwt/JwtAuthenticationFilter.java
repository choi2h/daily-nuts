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

        // 헤더 유/무 검사
        if (header == null || !header.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"1번째 검사\"}");
            return;
        }

        // 토큰 유효성 검사.
        // validateToken(token) 메서드가 전체적인 토큰의 유효성을 검사함
        String token = header.substring(7);
        if (!jwtUtils.validateToken(token)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"2번째 검사\"}");
            return;
        }


        // 검사가 통과되면 인증 컨텍스트에 추가
        String loginId = jwtUtils.getLoginIdFromToken(token);
        UserDetails userDetails = jwtService.convertToUserDetails(loginId);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 다음 필터로
        filterChain.doFilter(req, res);
    }

}