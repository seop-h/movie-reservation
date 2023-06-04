package com.theater.web.login;

import com.theater.domain.member.Member;
import com.theater.domain.member.repository.MemberRepository;
import com.theater.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public String login(@ModelAttribute LoginMember form, HttpServletRequest request) {
        Member loginMember = memberRepository.findById(form.getId());
        if (loginMember == null || loginMember.getPassword() != form.getPassword()) return "아이디 또는 비밀번호가 잘못되었습니다.";
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getId());
        return "로그인에 성공했습니다.";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            return "잘못된 요청입니다.";
        }
        session.invalidate();
        return "로그아웃에 성공했습니다.";
    }

}
