package com.theater.domain.ticket.repository;

import com.theater.domain.ticket.Ticket;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryTicketRepository implements TicketRepository {

    private static final Map<Integer, Ticket> store = new ConcurrentHashMap<>();
    private static Integer ticketSequence = 0;

    @Override
    public Ticket save(Ticket ticket) {
        ticket.setKey(++ticketSequence);
        ticket.setReservationTime(new Timestamp(System.currentTimeMillis())); //티켓이 생성된 시간 주입
        store.put(ticket.getKey(), ticket);
        return store.get(ticket.getKey());
    }

    @Override
    public Ticket findByKey(Integer ticketKey) {
        return store.get(ticketKey);
    }

    @Override
    public List<Ticket> findByMemberId(String memberId) {
        return store.values().stream()
                .filter(ticket -> (ticket.getMemberId() == memberId))
                .collect(Collectors.toList());
    }

    @Override
    public Ticket findByMemberAndSchedule(String memberId, Integer scheduleKey) {
        return store.values().stream()
                .filter(ticket -> (ticket.getMemberId() == memberId))
                .filter(ticket -> (ticket.getScheduleKey() == scheduleKey))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Boolean delete(Ticket ticket) {
        if (store.remove(ticket.getKey()) != null) return true;
        return false;
    }
}
