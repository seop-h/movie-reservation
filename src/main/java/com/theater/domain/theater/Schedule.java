package com.theater.domain.theater;

import lombok.Data;

import java.util.Date;

//상영일정 정보
@Data
public class Schedule {

    private Integer key; //상영일정번호
    private Integer movieKey; //영화번호
    private Integer screenKey; //상영관번호
    //TODO Date 타입 맞는지 체크
    private Date startTime; //상영시작일(요일 포함)

    public Schedule(Integer movieKey, Integer screenKey, Date startTime) {
        this.movieKey = movieKey;
        this.screenKey = screenKey;
        this.startTime = startTime;
    }
}
