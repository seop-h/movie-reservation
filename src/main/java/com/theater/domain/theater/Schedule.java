package com.theater.domain.theater;

import lombok.Data;

import java.time.LocalDateTime;

//상영일정 정보
@Data
public class Schedule {

    private Integer key; //상영일정번호
    private Integer movieKey; //영화번호
    private Integer screenKey; //상영관번호
    private String seatState = "0000000000"; //상영관 좌석 예약 상태(1번 ~ 10번 좌석까지 / 0: 빈좌석, 1: 예약좌석)
    private LocalDateTime startTime; //상영시작일

    public Schedule(Integer movieKey, Integer screenKey, LocalDateTime startTime) {
        this.movieKey = movieKey;
        this.screenKey = screenKey;
        this.startTime = startTime;
    }
}
