package com.dailynuts.security.config;

import com.dailynuts.security.jwt.JwtAuthenticationFilter;
import com.dailynuts.security.jwt.JwtUtils;
import com.dailynuts.security.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 이화님이 주신 프론트 cors 설정
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:5173")); // 프론트 도메인
                    configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")); // 허용 HTTP 메서드
                    configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
                    configuration.setAllowCredentials(true); // 인증정보 포함 허용
                    configuration.addExposedHeader("Authorization");
                    configuration.addExposedHeader("Refresh-Token");
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

                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, jwtService), UsernamePasswordAuthenticationFilter.class)

                //.addFilterAfter(new RefreshTokenFilter(jwtUtils, jwtService), JwtAuthenticationFilter.class)

                // 요청에 대한 인가(Authorization) 규칙 설정
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/member/signup", "/member/login", "/member/refresh", "/member/exist").permitAll()
                                .anyRequest().authenticated()
                );

        // 빌드 및 반환
        return http.build();
    }

}