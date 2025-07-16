package com.dailynuts.security.config;

import com.dailynuts.security.jwt.JwtAuthenticationFilter;
import com.dailynuts.security.jwt.JwtUtils;
import com.dailynuts.security.service.JwtService;
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

    // JWT로 인증을 처리하는 경우 호출하지 않지만
    // 그래도 갖고있는게 낫다고 함
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // HTTP 인증 회피용 메서드 (*수정 예정*)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                // 이화님이 주신 프론트 cors 설정
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:5173")); // 프론트 도메인
                    configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE")); // 허용 HTTP 메서드
                    configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
                    configuration.setAllowCredentials(true); // 인증정보 포함 허용
                    return configuration;
                 }))

                // CSRF (Cross-Site Request Forgery) 보호 비활성화
                // JWT와 같은 토큰 기반 인증에서는 세션을 사용하지 않으므로 CSRF 보호가 필요 없습니다.
                .csrf(AbstractHttpConfigurer::disable)

                // HTTP Basic 인증 비활성화 (기본 브라우저 팝업 인증)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 폼 기반 로그인 비활성화 (세션 기반의 기본 로그인 폼 사용 안 함)
                .formLogin(AbstractHttpConfigurer::disable)

                // 세션 관리 정책 설정: STATELESS
                // JWT는 무상태(Stateless) 인증이므로 세션을 사용하지 않도록 설정합니다.
                // 이렇게 하면 서버는 클라이언트의 상태를 저장하지 않습니다.
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 요청에 대한 인가(Authorization) 규칙 설정
                // antMatchers -> requestMatchers (Spring Security 6부터 권장)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // 특정 URL 패턴에 대한 접근 허용 (인증 없이 접근 가능)
                                // 로그인, 회원가입 API 등은 모든 사용자에게 허용해야 합니다.
                                .requestMatchers("/member/signup", "/member/login").permitAll() // 예: 로그인, 회원가입 API 경로

                                // 그 외의 모든 요청은 인증되어야만 접근 가능합니다.
                                .anyRequest().authenticated()
                )

                // 예외 처리 설정 (필요시 추가)
                // .exceptionHandling(exceptionHandling ->
                //     exceptionHandling
                //         .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 실패 시 처리
                //         .accessDeniedHandler(new CustomAccessDeniedHandler()) // 인가 실패 시 처리
                // )

                // 우리가 만든 JwtAuthenticationFilter를 Spring Security 필터 체인에 추가
                // UsernamePasswordAuthenticationFilter 이전에 실행되도록 설정합니다.
                // 이렇게 하면 JWT를 통해 인증된 사용자는 이후의 기본 인증 필터를 건너뛰게 됩니다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, jwtService), UsernamePasswordAuthenticationFilter.class);

        // 빌드 및 반환
        return http.build();
    }

}
