package com.theater.domain.theater;

import lombok.AllArgsConstructor;
import lombok.Data;

//좌석 정보
@Data
@AllArgsConstructor
public class Seat {

    private Integer seatKey; //좌석번호
    private Integer screenKey; //상영관번호
}
