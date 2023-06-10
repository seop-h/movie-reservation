package com.theater.domain.ticket.dto;

import com.theater.domain.member.service.MemberService;
import com.theater.domain.movie.service.MovieService;
import com.theater.domain.theater.service.ScheduleService;
import com.theater.domain.ticket.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketRegister {

    private final MemberService memberService;
    private final MovieService movieService;
    private final ScheduleService scheduleService;

    public List<TicketShowDto> changeToShowList(List<Ticket> ticketList) {
        List<TicketShowDto> dtoList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            dtoList.add(changeToShow(ticket));
        }
        return dtoList;
    }

    public TicketShowDto changeToShow(Ticket ticket) {
        return new TicketShowDto(
                ticket.getKey(),
                memberService.findMember(ticket.getMemberId()).getName(),
                movieService.findMovie(
                        scheduleService.findSchedule(ticket.getScheduleKey()
                        ).getMovieKey()).getTitle(),
                ticket.getScreenKey(),
                ticket.getSeat(),
                ticket.getPrice(),
                ticket.getReservationTime()
        );
    }

}
