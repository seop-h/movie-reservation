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

    @PostMapping("/login") //로그인(에러 발생시 errorCode: -100번대)
    public ResponseResult login(@RequestBody LoginMember form, @RequestParam(defaultValue = "/") String redirectURI,
                                HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("redirectURL={}", redirectURI);
        Member loginMember = memberService.findMember(form.getId());
        log.info("form={}, loginMember={}", form, loginMember);
        if (loginMember == null || !loginMember.getPassword().equals(form.getPassword())) //회원 정보와 로그인 입력 정보가 불일치하면 오류 반환
            return new ErrorResult("아이디 또는 비밀번호가 일치하지 않습니다.", -101);

        log.info("make new session");
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_ID, loginMember.getId()); //로그인에 성공하면 새로운 세션을 만들어 그곳에 회원 아이디 저장
        response.sendRedirect(redirectURI); //로그인 성공 시 원래 접근하려던 페이지로 리다이렉트(기본값: /)
        return new CorrectResult("로그인에 성공했습니다."); //TODO 리다이렉트 다음에 반환해봤자 안됨
    }

    @PostMapping("/logout") //로그아웃(에러 발생시 errorCode: -110번대)
    public ResponseResult logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); //이미 있는 세션을 가져옴(없으면 null 반환)
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER_ID) == null) { //로그인하지 않은 사용자가 로그아웃 요청 시 오류 반환
            return new ErrorResult("잘못된 요청입니다.", -111);
        }
        session.invalidate(); //세션 만료시킴
        return new CorrectResult("로그아웃 했습니다.");
    }

}
