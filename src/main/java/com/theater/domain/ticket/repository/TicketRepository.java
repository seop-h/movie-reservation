package com.theater.domain.ticket.repository;

import com.theater.domain.ticket.Ticket;
import com.theater.domain.ticket.dto.TicketShowDto;

import java.util.List;

//티켓 저장소 인터페이스
public interface TicketRepository {

    Ticket save(Ticket ticket); //티켓 저장(구매)

    Ticket findByKey(Integer ticketKey); //티켓 조회

    List<Ticket> findByMemberId(String memberId); //회원아이디로 티켓 리스트 조회

    Ticket findByMemberAndSchedule(String memberId, Integer scheduleKey); //회원아이디와 상영정보로 티켓 조회

    Boolean delete(Ticket ticket); //티켓 삭제(환불)

}
