package com.theater.web.member;

import com.theater.domain.member.Member;
import com.theater.domain.member.service.MemberService;
import com.theater.web.responsedata.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    //TODO 동일한 아이디로 회원가입하는 경우 막기
    @PostMapping
    public ResponseResult signUp(@RequestBody Member member) {
        Member saveMember = memberService.join(member);
        log.info("save new member={}", saveMember);
        return new ResponseResult("회원가입에 성공했습니다.");
    }

    @PostConstruct
    private void initMemberSave() { //초기 회원 데이터 세팅(메모리 사용 시 필요)
        memberService.join(new Member("111", "test1!", "aaa", "010-0000-0000", "aaa@gmail.com"));
        memberService.join(new Member("222", "test2!", "bbb", "010-1111-1111", "bbb@gmail.com"));
        memberService.join(new Member("333", "test3!", "ccc", "010-2222-2222", "ccc@gmail.com"));
        memberService.join(new Member("444", "test4!", "ddd", "010-3333-3333", "ddd@gmail.com"));
        memberService.join(new Member("555", "test5!", "eee", "010-4444-4444", "eee@gmail.com"));
    }

}
