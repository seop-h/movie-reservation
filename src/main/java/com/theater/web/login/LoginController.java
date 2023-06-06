package com.theater.web.login;

import com.theater.domain.member.Member;
import com.theater.domain.member.service.MemberService;
import com.theater.web.SessionConst;
import com.theater.web.responsedata.extension.CorrectResult;
import com.theater.web.responsedata.extension.ErrorResult;
import com.theater.web.responsedata.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseResult login(@RequestBody LoginMember form, @RequestParam(defaultValue = "/") String redirectURI,
                                HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("redirectURL={}", redirectURI);
        Member loginMember = memberService.findMember(form.getId());
        log.info("form={}, loginMember={}", form, loginMember);
        if (loginMember == null || !loginMember.getPassword().equals(form.getPassword()))
            return new ErrorResult("아이디 또는 비밀번호가 일치하지 않습니다.", -101);

        log.info("make new session");
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_ID, loginMember.getId());
        response.sendRedirect(redirectURI); //로그인 성공 시 원래 접근하려던 페이지로 리다이렉트(기본값: /)
        return new CorrectResult("로그인에 성공했습니다."); //TODO 리다이렉트 다음에 반환해봤자 안됨
    }

    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER_ID) == null) {
            return new ErrorResult("잘못된 요청입니다.", -111);
        }
        session.invalidate();
        return new CorrectResult("로그아웃 했습니다.");
    }

}
