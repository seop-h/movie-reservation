package com.theater.web.interceptor;

import com.theater.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);//이미 존재하는 세션을 가져온다

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER_ID) == null) {//로그인하지 않은 사용자 접근 시 로그인페이지로 리다이렉트
            log.info("미인증 사용자 요청");

            response.sendRedirect("/login?redirectURI=" + requestURI);
            return false;
        }

        return true;
    }

}
