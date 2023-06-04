package com.theater.web.argumentresolver;

import com.theater.domain.member.Member;
import com.theater.domain.member.service.MemberService;
import com.theater.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        log.info("@Login: supportsParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasCorrectType = (Member.class.isAssignableFrom(parameter.getParameterType())
                || String.class.isAssignableFrom(parameter.getParameterType()));

        return hasLoginAnnotation && hasCorrectType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("@Login: resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalAccessException("로그인하지 않은 사용자가 접근");
        }

        String loginMemberId = (String) session.getAttribute(SessionConst.LOGIN_MEMBER_ID);
        if (loginMemberId == null) {
            throw new IllegalAccessException("로그인하지 않은 사용자가 접근");
        }
        log.info("@Login: member id={}", loginMemberId);
        if (String.class.isAssignableFrom(parameter.getParameterType())) { //@Login이 붙은 파라미터의 타입이 String이면 아이디를 반환
            return loginMemberId;
        } else { //@Login이 붙은 파라미터의 타입이 Member이면 member를 반환
            return memberService.findMember(loginMemberId);
        }
    }

}
