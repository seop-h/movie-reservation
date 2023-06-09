package com.theater.domain.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TicketShowDto {

    private Integer key; //티켓번호
    private String memberName; //회원이름
    private String title; //영화제목
    private Integer screenKey; //상영관번호
    private Integer seat; //좌석번호
    private Integer price; //티켓가격
    private Timestamp reservationTime; //결제시간

}
