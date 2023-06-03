package com.theater.domain.theater;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.DateFormat;

//상영일정 정보
@Data
@AllArgsConstructor
public class Schedule {

    private Integer key; //상영일정번호
    private Integer movieKey; //영화번호
    private Integer screenKey; //상영관번호
    //TODO DateFormat 타입 맞는지 체크
    private DateFormat startTime; //상영시작일(요일 포함)

}
