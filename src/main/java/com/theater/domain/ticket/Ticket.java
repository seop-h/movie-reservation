package com.theater.domain.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

//티켓 정보
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private Integer key; //티켓번호
    private String memberId; //회원아이디
    private Integer scheduleKey; //상영일정번호
    private Integer screenKey; //상영관번호
    private Integer seat; //좌석번호
    private Integer price; //티켓가격
    private Timestamp reservationTime; //결제시간

    public Ticket(Integer scheduleKey, Integer screenKey, Integer seat) {
        this.scheduleKey = scheduleKey;
        this.screenKey = screenKey;
        this.seat = seat;
    }
}
