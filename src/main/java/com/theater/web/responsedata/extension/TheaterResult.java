package com.theater.web.responsedata.extension;

import com.theater.domain.theater.dto.ScheduleShowDto;
import com.theater.web.responsedata.ResponseResult;
import lombok.Getter;

import java.util.List;

@Getter
public class TheaterResult extends ResponseResult {

    private List<ScheduleShowDto> scheduleList;

    public TheaterResult(String message, List<ScheduleShowDto> scheduleList) {
        super(message);
        this.scheduleList = scheduleList;
    }
}
