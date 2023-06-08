package com.theater.web.responsedata.extension;

import com.theater.domain.theater.Schedule;
import com.theater.web.responsedata.ResponseResult;
import lombok.Getter;

import java.util.List;

@Getter
public class TheaterResult extends ResponseResult {

    private List<Schedule> scheduleList;

    public TheaterResult(String message, List<Schedule> scheduleList) {
        super(message);
        this.scheduleList = scheduleList;
    }
}
