package com.theater.web.theater;

import com.theater.domain.theater.Schedule;
import com.theater.domain.theater.ScheduleSearchCond;
import com.theater.domain.theater.Screen;
import com.theater.domain.theater.dto.ScheduleRegister;
import com.theater.domain.theater.dto.ScheduleShowDto;
import com.theater.domain.theater.service.ScheduleService;
import com.theater.domain.theater.service.ScreenService;
import com.theater.domain.ticket.service.TicketService;
import com.theater.web.responsedata.ResponseResult;
import com.theater.web.responsedata.extension.TheaterResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/theater")
@RequiredArgsConstructor
@Slf4j
public class TheaterController {

    //로그인되지 않은 사용자 접근 시: errorCode = -400

    private final ScreenService screenService;
    private final ScheduleService scheduleService;

    private final ScheduleRegister register;

    @GetMapping //쿼리 파라미터로 받은 데이터에 해당하는 상영일정 조회. 쿼리 파라미터가 없으면 모든 상영일정 조회(에러 발생시 errorCode: -400번대)
    public ResponseResult findMovieSchedule(@RequestParam(required = false) Integer movie,
                                            @RequestParam(required = false) Integer screen) {

        if (movie == null && screen == null) { //쿼리 파라미터가 없으면 모든 상영일정 조회
            log.info("모든 상영일정 조회");
            //조회한 상영일정을 사용자가 보기 용이한 스펙으로 변경하여 반환 ( register.changeToShowList() )
            return new TheaterResult("상영일정 조회 성공", register.changeToShowList(scheduleService.findAllSchedule()));
        }

        //쿼리 파라미터가 있으면 그 조건에 맞는 상영일정 조회
        log.info("조건에 맞는 상영일정 조회");
        ScheduleSearchCond cond = new ScheduleSearchCond(movie, screen); //쿼리 파라미터에 따라 조건 설정
        //조회한 상영일정을 사용자가 보기 용이한 스펙으로 변경하여 반환 ( register.changeToShowList() )
        return new TheaterResult("상영일정 조회 성공", register.changeToShowList(scheduleService.findAllSchedule(cond)));
    }

    @PostConstruct
    private void initScreenScheduleSave() { //초기 상영관(3개) 및 상영일정(5개) 데이터 세팅(메모리 사용 시 필요)
        log.info("[Theater > Screen] initialize Screen Repository");
        screenService.addScreen(new Screen());
        screenService.addScreen(new Screen());
        screenService.addScreen(new Screen());


        log.info("[Theater > Schedule] initialize Schedule Repository");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        scheduleService.addSchedule(new Schedule(1, 1, 10000, LocalDateTime.parse("2023-06-08 10:30", formatter)));
        scheduleService.addSchedule(new Schedule(2, 1, 12000, LocalDateTime.parse("2023-06-08 14:30", formatter)));
        scheduleService.addSchedule(new Schedule(3, 2, 10000, LocalDateTime.parse("2023-06-08 09:30", formatter)));
        scheduleService.addSchedule(new Schedule(4, 2, 12000, LocalDateTime.parse("2023-06-08 13:30", formatter)));
        scheduleService.addSchedule(new Schedule(5, 3, 12000, LocalDateTime.parse("2023-06-08 11:30", formatter)));
    }

}
