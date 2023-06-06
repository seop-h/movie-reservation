package com.theater.web.member;

import com.theater.domain.member.Member;
import com.theater.domain.member.service.MemberService;
import com.theater.web.argumentresolver.Login;
import com.theater.web.responsedata.extension.CorrectResult;
import com.theater.web.responsedata.ResponseResult;
import com.theater.web.responsedata.extension.ErrorResult;
import com.theater.web.responsedata.extension.MemberResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    //TODO 동일한 아이디로 회원가입하는 경우 막기
    @PostMapping //회원가입
    public ResponseResult signUp(@RequestBody Member member) {
        if (memberService.findMember(member.getId()) != null) {
            return new ErrorResult("이미 존재하는 아이디입니다.", -201);
        }
        Member saveMember = memberService.join(member);
        log.info("save new member={}", saveMember);
        return new CorrectResult("회원가입에 성공했습니다.");
    }

    @GetMapping //자신의 회원 정보 조회
    public ResponseResult showMember(@Login String memberId) {
        Member member = memberService.findMember(memberId);
        MemberShowDto memberDto = MemberRegister.change(member);
        return new MemberResult("회원 조회 성공", memberDto);
    }

    @PostConstruct
    private void initMemberSave() { //초기 회원 데이터 세팅(메모리 사용 시 필요)
        log.info("[Member] initialize Member Repository");
        memberService.join(new Member("111", "test1!", "aaa", "010-0000-0000", "aaa@gmail.com"));
        memberService.join(new Member("222", "test2!", "bbb", "010-1111-1111", "bbb@gmail.com"));
        memberService.join(new Member("333", "test3!", "ccc", "010-2222-2222", "ccc@gmail.com"));
        memberService.join(new Member("444", "test4!", "ddd", "010-3333-3333", "ddd@gmail.com"));
        memberService.join(new Member("555", "test5!", "eee", "010-4444-4444", "eee@gmail.com"));
    }

}
