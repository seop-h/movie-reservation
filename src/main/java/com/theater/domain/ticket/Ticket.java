package com.theater.domain.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

//티켓 정보
@Data
@AllArgsConstructor
public class Ticket {

    private Integer key; //티켓번호
    private Integer scheduleKey; //상영일정번호
    private Integer screenKey; //상영관번호
    private Integer seat; //좌석번호
    private Integer reservationKey; //예매번호
    private Integer price; //티켓가격

}
