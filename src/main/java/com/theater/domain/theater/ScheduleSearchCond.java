package com.theater.domain.theater;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleSearchCond {

    private Integer movieKey; //영화번호
    private Integer screenKey; //상영관번호
    //TODO 상영시작일(startTime)도 추후에 포함하도록 구현

}
