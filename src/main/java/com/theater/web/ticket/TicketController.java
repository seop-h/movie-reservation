package com.theater.web.ticket;

import com.theater.domain.ticket.Ticket;
import com.theater.domain.ticket.dto.TicketRegister;
import com.theater.domain.ticket.service.TicketService;
import com.theater.web.argumentresolver.Login;
import com.theater.web.responsedata.ResponseResult;
import com.theater.web.responsedata.extension.CorrectResult;
import com.theater.web.responsedata.extension.ErrorResult;
import com.theater.web.responsedata.extension.TicketResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.management.AttributeNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    //로그인되지 않은 사용자 접근 시: errorCode = -500

    private final TicketService ticketService;

    private final TicketRegister register;

    @PostMapping //티켓 구매(에러 발생시 errorCode: -500번대)
    public ResponseResult buyTicket(@Login String memberId, @RequestBody Ticket form) throws AttributeNotFoundException {

        Ticket ticket = ticketService.buy(memberId, form); //form에 맞는 티켓을 생성 후 구매
        if (ticket == null) { //회원의 보유 금액이 부족하거나 선택한 좌석이 이미 예약된 경우 오류 반환
            return new CorrectResult("잔액이 부족하거나 이미 예매된 좌석입니다.");
        }

        //구매한 티켓을 사용자가 보기 용이한 스펙으로 변경하여 반환 ( register.changeToShow() )
        return new TicketResult("티켓 구매 성공", register.changeToShow(ticket));

    }

    @GetMapping// 자신의 티켓 조회. 쿼리 파라미터에 따라 조회 방식이 달라짐(에러 발생시 errorCode: -510번대)
    public ResponseResult showTickets(@Login String memberId,
                                      @RequestParam(required = false) Integer ticket,
                                      @RequestParam(required = false) Integer schedule) {

        if (ticket == null && schedule == null) { //1. 내 티켓 전체를 조회하는 경우
            List<Ticket> ticketList = ticketService.findMyAllTickets(memberId);
            //조회한 티켓을 사용자가 보기 용이한 스펙으로 변경하여 반환 ( register.changeToShowList() )
            return new TicketResult("모든 내 티켓 조회 성공", register.changeToShowList(ticketList));
        } else if (ticket != null && schedule == null) { //2. 특정 티켓만 티켓 번호로 조회하는 경우
            Ticket myTicket = ticketService.findMyTicket(ticket);
            //티켓 번호에 해당하는 티켓이 없거나 해당 티켓의 회원아이디가 내 아이디와 다른 경우 오류 반환
            if (myTicket == null || !myTicket.getMemberId().equals(memberId)) {
                log.info("[finding ticket key] myTicket.getMemberId != memberId: myTicket={}, memberId={}", myTicket, memberId);
                return new ErrorResult("잘못된 요청입니다. 내가 구매한 티켓이 아닙니다.", -511);
            }
            //조회한 티켓을 사용자가 보기 용이한 스펙으로 변경하여 반환 ( register.changeToShow() )
            return new TicketResult("티켓 번호로 내 티켓 조회 성공", register.changeToShow(myTicket));
        } else if (ticket == null && schedule != null) { //3. 특정 티켓만 스케줄 번호와 내 아이디로 조회하는 경우
            Ticket myTicket = ticketService.findMyTicket(memberId, schedule);
            //그 상영일정에 해당하는 티켓이 없거나 해당 티켓의 회원아이디가 내 아이디와 다른 경우 오류 반환
            if (myTicket == null || !myTicket.getMemberId().equals(memberId)) {
                log.info("[finding schedule key] myTicket.getMemberId != memberId: myTicket={}, memberId={}", myTicket, memberId);
                return new ErrorResult("잘못된 요청입니다. 내가 구매한 티켓이 아닙니다.", -512);
            }
            //조회한 티켓을 사용자가 보기 용이한 스펙으로 변경하여 반환 ( register.changeToShow() )
            return new TicketResult("아이디와 상영일정으로 내 티켓 조회 성공", register.changeToShow(myTicket));
        } else { //4. 위의 로직에서 반환이 되지 않고 이곳까지 진행되면 정해놓은 쿼리 파라미터 조건에 부합하지 않았다는 것. 이때는 예외 발생시킴
            throw new IllegalArgumentException("쿼리 파라미터 조건이 일치하지 않음");
        }
    }

    @DeleteMapping("/{ticketKey}") //티켓 환불(에러 발생시 errorCode: -520번대)
    public ResponseResult refundTicket(@Login String memberId, @PathVariable Integer ticketKey) {
        Ticket myTicket = ticketService.findMyTicket(ticketKey);
        //티켓 번호에 해당하는 티켓이 없거나 해당 티켓의 회원아이디가 내 아이디와 다른 경우 오류 반환
        if (myTicket == null || !myTicket.getMemberId().equals(memberId))
            return new ErrorResult("잘못된 요청입니다. 내가 구매한 티켓이 아닙니다.", -521);
        ticketService.refund(ticketKey); //티켓 환불
        return new CorrectResult("티켓을 성공적으로 환불했습니다.");
    }

}
