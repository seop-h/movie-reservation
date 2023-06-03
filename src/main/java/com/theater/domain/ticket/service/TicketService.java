package com.theater.domain.ticket.service;

import com.theater.domain.member.Member;
import com.theater.domain.member.repository.MemberRepository;
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

    public Ticket buy(Ticket ticket) {
        Member member = memberRepository.findById(ticket.getMemberId());
        int money = member.getMoney();
        int price = ticket.getPrice();
        if (money >= price) {
            member.setMoney(money - price);
            return ticketRepository.save(ticket);
        } else {
            return null;
        }
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
        return ticketRepository.delete(ticket);
    }

}
