package com.theater.web.interceptor;

import com.theater.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        log.info("[LoginCheckInterceptor] request: {} \"{}\"", method, requestURI);

        // POST "/members" 요청은 인터셉터 통과
        //왜냐하면 이 요청은 회원가입이기 때문에 인터셉터에서 로그인하지 않은 사용자인지 여부를 따지면 안됨
        if (requestURI.equals("/members") && method.equals("POST")) return true;

        HttpSession session = request.getSession(false);//이미 존재하는 세션을 가져온다
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER_ID) == null) {//로그인하지 않은 사용자 접근 시 예외 발생
            log.info("[LoginCheckInterceptor] 미인증 사용자 요청");
            throw new AuthenticationException("로그인하지 않은 사용자의 요청");
        }

        return true;
    }

}
