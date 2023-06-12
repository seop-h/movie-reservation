package com.theater.web.member;

import com.theater.domain.member.Member;
import com.theater.domain.member.dto.*;
import com.theater.domain.member.service.MemberService;
import com.theater.domain.ticket.Ticket;
import com.theater.domain.ticket.service.TicketService;
import com.theater.web.argumentresolver.Login;
import com.theater.web.responsedata.extension.CorrectResult;
import com.theater.web.responsedata.ResponseResult;
import com.theater.web.responsedata.extension.ErrorResult;
import com.theater.web.responsedata.extension.MemberResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final TicketService ticketService;

    //로그인되지 않은 사용자 접근 시: errorCode = -200

    @PostMapping //회원가입(에러 발생시 errorCode: -200번대)
    public ResponseResult signUp(@RequestBody Member member) {
        if (memberService.findMember(member.getId()) != null) { //이미 해당 아이디로 누군가 회원가입을 했으면 오류 반환
            return new ErrorResult("이미 존재하는 아이디입니다.", -201);
        }
        Member saveMember = memberService.join(member); //회원 정보 저장
        log.info("save new member={}", saveMember);
        return new CorrectResult("회원가입에 성공했습니다.");
    }

    @GetMapping //자신의 회원 정보 조회(아이디, 이름, 휴대폰번호, 이메일, 보유금액)(에러 발생시 errorCode: -210번대)
    public ResponseResult showMember(@Login Member member) {
        MemberShowDto memberDto = MemberRegister.changeToShow(member); //회원 정보를 조회에 용이한 스펙으로 변환시켜서 보여줌
        return new MemberResult("회원 조회 성공", memberDto);
    }

    @PutMapping //회원 정보 수정(이름, 휴대폰번호, 이메일)(에러 발생시 errorCode: -220번대)
    public ResponseResult updateMember(@Login String memberId, @RequestBody MemberUpdateDto memberDto) {
        Member updateMember = memberService.modify(memberId, memberDto); //memberId에 해당하면 회원의 정보를 memberDto에 맞게 수정
        return new MemberResult("회원 정보 변경 성공", MemberRegister.changeToShow(updateMember));
    }

    @PatchMapping("/password") //비밀번호 변경. 변경 시 자동 로그아웃(에러 발생시 errorCode: -230번대)
    public ResponseResult updatePassword(@Login String memberId, @RequestBody MemberPasswordDto memberDto, HttpServletRequest request) {
        memberService.modify(memberId, memberDto.getPassword()); //memberId에 해당하면 회원의 비밀번호를 memberDto에 맞게 수정
        HttpSession session = request.getSession(false);
        session.invalidate(); //세션 만료시킴(로그아웃)
        return new CorrectResult("비밀번호를 변경했습니다. 새로운 비밀번호로 다시 로그인 해주세요");
    }

    @PatchMapping("/money") //금액 충전(에러 발생시 errorCode: -240번대)
    public ResponseResult loadMoney(@Login String memberId, @RequestBody MemberMoneyDto memberDto) {
        Integer loadedMoney = memberService.loadMoney(memberId, memberDto.getMoney()); //memberId에 해당하면 회원의 보유금액을 memberDto만큼 충전
        return new MemberResult("보유금액 충전 성공", loadedMoney);
    }

    @DeleteMapping //회원 탈퇴. 탈퇴 시 비밀번호 입력 -> 탈퇴 완료 후 세션 만료시킴(에러 발생시 errorCode: -250번대)
    public ResponseResult withdrawMember(@Login Member member, @RequestBody MemberPasswordDto memberDto,
                                         HttpServletRequest request) {
        String password = memberDto.getPassword(); //회원 탈퇴 시 비밀번호 검사 진행
        if (member.getPassword().equals(password)) {
            //해당 회원과 관련된 티켓 정보도 모두 삭제
            List<Ticket> myAllTickets = ticketService.findMyAllTickets(member.getId());
            for (Ticket ticket : myAllTickets) {
                ticketService.refund(ticket.getKey());
            }

            memberService.withdraw(member); //회원 탈퇴 진행
            HttpSession session = request.getSession(false);
            session.invalidate(); //세션 만료시킴
            return new CorrectResult("탈퇴를 성공적으로 진행했습니다.");
        } else { //비밀번호가 일치하지 않으면 오류 반환
            return new ErrorResult("비밀번호가 맞지 않습니다.", -251);
        }
    }

   @PostConstruct
    private void initMemberSave() { //초기 회원 데이터 세팅
        log.info("[Member] initialize Member Repository");
        memberService.join(new Member("test", "test!", "tester", "010-0000-0000", "aaa@gmail.com"));
    }

}
