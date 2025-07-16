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

// OncePerRequestFilter 이거 상속하면
// 모든 API 요청마다 딱 한 번 이 필터를 거치는거임.
// dispatcher Servlet에 진입하기 직전에 하는 필터 검사 같은 느낌
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final JwtService jwtService;

    // 순차적으로 분기를 하나씩 건너면서
    // 매개변수 HttpServletRequest 속에 있는 cookie("token")의 value(jwt)를 파싱하고
    // 시큐리티 컨텍스트(인증 완료된 녀석들을 담아놓는 곳)에서 원하는 타입으로 파싱된 데이터를 다듬고
    // 다듬은 회원 객체(JwtMember)를 컨텍스트에 저장, 이후에는 그 저장된 데이터를 쓸 수 있음.
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String token = null;

        // httpServlet요청으로 날아온 cookie들을 모두 가져옴
        // 가장 첫번째 분기가 쿠키가 존재하는지에 대한 검사를 함
        Cookie[] cookies = req.getCookies();

        if (cookies == null || cookies.length == 0) {

            // 필터 체인은 여러 필터로 연결되어 있음
            // 체인을 하나씩 뒤로 넘기면서 마지막 필터까지 전달함
            // 최종 필터에서 doFilter메서드를 사용하면 dispatcher Servlet으로 제어가 넘어감

            // 여기선 cookie가 없는 경우 즉, 로그인이나 회원가입 같은 경우
            // 시큐리티 컨텍스트에 데이터를 저장할 쿠키(토큰)이 없으니
            // 그냥 다음 필터로 넘기고 메서드를 종료시킴 (컨텍스트에 데이터가 없어도 실행 가능한 API는 따로 설정)
            // 만약 로그인(쿠키를 갖고있는 상태)을 하고 나면, 요청이 올때 다음 분기로 넘어갈 수 있음
            filterChain.doFilter(req,res);
            return;
        }

        // 이번 분기는 쿠키안에 value값으로 토큰이 있는지 검사함.
        for (Cookie cookie : cookies){
            // for문으로 하나하나 찾아서 검사
            if("token".equals(cookie.getName())){
                token = cookie.getValue();
                break;
            }
        }

        // 순회 공연 돌면서 "token"이라는 key 안에 value를 넣어줬는데
        // 만약에 없다? 여기까지도 로그인 / 회원가입 API라고 생각하고 넘김
        // 이유는 쿠키와 토큰은 동시에 발급하기 때문에
        // 쿠키가 없으면 토큰도 없고 토큰이 없으면 쿠키도 없기 때문
        // 쿠키를 먼저 검사한 이유는 최종적으로 쿠키 안에 토큰을 담기 때문에 바깥 껍질부터 벗기듯
        // 검사하는것.
        if (token == null) {
            filterChain.doFilter(req,res);
            return;
        }

        String loginId = null;

        // 여기까지 왔다?
        // 이번 요청으로 넘어온 녀석은 어쨋거나 로그인이 된 상태라는것
        // 로그인이 됐다는 것은 쿠키(토큰)이 있다는것
        // 근데 내가 발급한 토큰이 유효한지(만료시간, 존재여부, 블랙리스트) 등..
        // validateToken메서드에서 검사를 함.
        if(jwtUtils.validateToken(token)){
            // 만약 예외없이 파싱 됐다면 드디어 그 토큰으로 loginId를 가져옴.
            // 사실 여기까지 왔다면 유효성 검사는 충분하지만
            // 시큐리티 컨텍스트는 입맛이 까다로운 녀석이라 UserDetails타입의 재료가 있어야함
            loginId = jwtUtils.getLoginIdFromToken(token);
        } else {
            throw new CustomException(CustomErrorCode.TOKEN_NOT_VAILD);
        }

        // 대충 앞에 있는 모든 분기를 잘 통과했다면?
        // LoginId로 member객체를 찾아낸 후
        // member객체를 JwtMember(UserDetails의 구현체)의 생성자 매개변수로 사용함
        // 그럼 요리재료로 UserDetails가 준비가 된 상태. 이제 이걸로
        // UsernamePasswordAuthenticationToken 이거 조립하면 됨.
        if (loginId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.cookByLoginId(loginId);

            if (userDetails != null) {
                // UsernamePasswordAuthenticationToken 인증 객체 생성
                // SpringSecurity에서 유저 아이디와 패스워드로 검증하는 토큰을 발행
                // 시큐리티 컨텍스트에 저장하려면 이 인증을 통과한 토큰이 있어야함.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // 보안 컨텍스트에 인증 토큰을 저장
                // 생명주기는 API요청과 함께 넘어온 HttpServletRequest가 처리되는 동안 유지됨
                // 여기 저장된 인증 토큰은 위에 있는 UsernamePasswordAuthenticationToken와 동일
                // 변수 authentication안에는 UserDetails가 데이터로 들어가 있는데
                // UserDetails는 jwtService.findByLoginId(loginId)의 결과물인 JwtMember가 들어가 있음
                // 그냥 타입만 UserDetails 라고 되어있지 사실상 MemberInfoDto 같은 놈임.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 모든 분기를 완전히 돌았다면?
        // 시큐리티 컨텍스트에 저장완료. 이제 controller메서드 실행하러 출발~
        filterChain.doFilter(req, res);
    }

}
