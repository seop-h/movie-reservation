package com.theater.domain.ticket.service;

import com.theater.domain.member.Member;
import com.theater.domain.member.repository.MemberRepository;
import com.theater.domain.theater.Schedule;
import com.theater.domain.theater.repository.ScheduleRepository;
import com.theater.domain.ticket.Ticket;
import com.theater.domain.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    public Ticket buy(String memberId, Ticket ticket) {
        Member member = memberRepository.findById(memberId);
        int money = member.getMoney();

        Schedule schedule = scheduleRepository.findByKey(ticket.getScheduleKey());
        int price = schedule.getPrice();

        String seatState = schedule.getSeatState();
        if (money < price || seatState.charAt(ticket.getSeat() - 1) == '1') {
            return null;
        }
        member.setMoney(money - price);
        scheduleRepository.occupySeat(schedule, ticket.getSeat());
        ticket.setScreenKey(schedule.getScreenKey());
        ticket.setMemberId(memberId);
        ticket.setPrice(price);
        return ticketRepository.save(ticket);
    }

    public Ticket findMyTicket(Integer ticketKey) {
        return ticketRepository.findByKey(ticketKey);
    }

    public Ticket findMyTicket(String memberId, Integer scheduleKey) {
        return ticketRepository.findByMemberAndSchedule(memberId, scheduleKey);
    }

    public List<Ticket> findMyAllTickets(String memberId) {
        return ticketRepository.findByMemberId(memberId);
    }

    public Boolean refund(Integer ticketKey) {
        Ticket ticket = ticketRepository.findByKey(ticketKey);
        if (ticket == null) return false;

        Member member = memberRepository.findById(ticket.getMemberId());
        member.setMoney(member.getMoney() + ticket.getPrice());

        Schedule schedule = scheduleRepository.findByKey(ticket.getScheduleKey());
        scheduleRepository.dropSeat(schedule, ticket.getSeat());
        return ticketRepository.delete(ticket);
    }

}
