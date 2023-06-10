package com.theater.web.responsedata.extension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.theater.domain.ticket.Ticket;
import com.theater.domain.ticket.dto.TicketRegister;
import com.theater.domain.ticket.dto.TicketShowDto;
import com.theater.web.responsedata.ResponseResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) //Json 데이터로 반환할 떄 null인 필드는 표시하지 않음
public class TicketResult extends ResponseResult {

    private List<TicketShowDto> ticketList;
    private TicketShowDto ticket;

    public TicketResult(String message, List<TicketShowDto> ticketList) {
        super(message);
        this.ticketList = ticketList;
    }

    public TicketResult(String message, TicketShowDto ticket) {
        super(message);
        this.ticket = ticket;
    }
}
