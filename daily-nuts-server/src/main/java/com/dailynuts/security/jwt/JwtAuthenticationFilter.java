package com.dailynuts.security.jwt;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        // req에서 token 꺼내기
        String token = null;

        Cookie[] cookies = req.getCookies();

        if (cookies == null || cookies.length == 0) {
            filterChain.doFilter(req,res);
            return;
        }

        for (Cookie cookie : cookies){
            if("token".equals(cookie.getName())){
                token = cookie.getValue();
                break;
            }
        }

        if (token == null) {
            filterChain.doFilter(req,res);
            return;
        }

        String loginId = null;

        // token 유효성 검사
        // token 파싱 -> loginId
        if(jwtUtils.validateToken(token)){
            loginId = jwtUtils.getLoginIdFromToken(token);
        } else {
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VAILD);
        }

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