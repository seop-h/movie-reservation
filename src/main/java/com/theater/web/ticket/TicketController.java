package com.theater.web.ticket;

import com.theater.domain.ticket.Ticket;
import com.theater.domain.ticket.dto.TicketRegister;
import com.theater.domain.ticket.dto.TicketShowDto;
import com.theater.domain.ticket.service.TicketService;
import com.theater.web.argumentresolver.Login;
import com.theater.web.responsedata.ResponseResult;
import com.theater.web.responsedata.extension.CorrectResult;
import com.theater.web.responsedata.extension.ErrorResult;
import com.theater.web.responsedata.extension.TicketResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO Schedule에서 register 관련된 것도 TicketController처럼 바꾸기
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    private final TicketService ticketService;

    private final TicketRegister register;

    //TODO 티켓 구입하면 상영관 좌석 예약 표시하는 기능 마저 구현
    @PostMapping
    public ResponseResult buyTicket(@Login String memberId, @RequestBody Ticket form) {

        Ticket ticket = ticketService.buy(memberId, form);
        if (ticket == null) {
            return new CorrectResult("잔액이 부족하거나 이미 예매된 좌석입니다.");
        }
        return new TicketResult("티켓 구매 성공", register.changeToShow(ticket));

    }

    @GetMapping// 자신의 티켓 조회. 쿼리 파라미터에 따라 조회 방식이 달라짐
    public ResponseResult showTickets(@Login String memberId,
                                      @RequestParam Boolean all,
                                      @RequestParam(required = false) Integer ticket,
                                      @RequestParam(required = false) Integer schedule) {

        if (all == true) {
            List<Ticket> ticketList = ticketService.findMyAllTickets(memberId);
            return new TicketResult("모든 내 티켓 조회 성공", register.changeToShowList(ticketList));
        }
        if (ticket != null) { //티켓 번호로 조회
            Ticket myTicket = ticketService.findMyTicket(ticket);
            if (!myTicket.getMemberId().equals(memberId)) {
                log.info("myTicket.getMemberId != memberId: myTicket={}, memberId={}", myTicket.getMemberId(), memberId);
                return new ErrorResult("잘못된 요청입니다. 내가 구매한 티켓이 아닙니다.", -401);
            }
            return new TicketResult("티켓 번호로 내 티켓 조회 성공", register.changeToShow(myTicket));
        }
        if (schedule != null) { //스케줄 번호와 내 아이디로 조회
            Ticket myTicket = ticketService.findMyTicket(memberId, schedule);
            return new TicketResult("아이디와 상영일정으로 내 티켓 조회 성공", register.changeToShow(myTicket));
        }
        throw new IllegalArgumentException("쿼리 파라미터 관련 조건이 일치하지 않음");
    }

}
