package com.dailynuts.security.config;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.security.jwt.JwtAuthenticationFilter;
import com.dailynuts.security.jwt.JwtUtils;
import com.dailynuts.security.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // JWT로 인증을 처리하는 경우 호출 X
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                // 이화님이 주신 프론트 cors 설정
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:5173")); // 프론트 도메인
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // 허용 HTTP 메서드
                    configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
                    configuration.setAllowCredentials(true); // 인증정보 포함 허용
                    return configuration;
                }))

                // CSRF (Cross-Site Request Forgery) 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // HTTP Basic 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                // 폼 기반 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable)

                // 세션 관리 정책 설정: STATELESS
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 요청에 대한 인가(Authorization) 규칙 설정
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().permitAll()
                )


                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((req, res, authEx) -> {
                                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    res.setContentType("application/json;charset=UTF-8");
                                    res.getWriter().write("{\"error\": \"TOKEN_NOT_VALID\"}");
                                })
                                .accessDeniedHandler((req, res, accessEx) -> {
                                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    res.setContentType("application/json;charset=UTF-8");
                                    res.getWriter().write("{\"error\": \"PERMISSION_DENIED\"}");
                                })
                )

                // JwtAuthenticationFilter를 Spring Security 필터 체인에 추가
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, jwtService), UsernamePasswordAuthenticationFilter.class);

        // 빌드 및 반환
        return http.build();
    }

}