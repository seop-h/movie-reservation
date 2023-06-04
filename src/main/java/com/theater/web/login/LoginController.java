package com.theater.web.login;

import com.theater.domain.member.Member;
import com.theater.domain.member.repository.MemberRepository;
import com.theater.domain.member.service.MemberService;
import com.theater.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    public String login(@ModelAttribute LoginMember form, @RequestParam(defaultValue = "/") String redirectURI,
                        HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("redirectURL={}", redirectURI);
        Member loginMember = memberService.findMember(form.getId());
        log.info("form={}, loginMember={}", form, loginMember);
        if (loginMember == null || !loginMember.getPassword().equals(form.getPassword())) return "아이디 또는 비밀번호가 잘못되었습니다.";

        log.info("make new session");
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_ID, loginMember.getId());
        response.sendRedirect(redirectURI); //로그인 성공 시 원래 접근하려던 페이지로 리다이렉트(기본값: /)
        return "로그인에 성공했습니다.";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER_ID) == null) {
            return "잘못된 요청입니다.";
        }
        session.invalidate();
        return "로그아웃에 성공했습니다.";
    }

}
