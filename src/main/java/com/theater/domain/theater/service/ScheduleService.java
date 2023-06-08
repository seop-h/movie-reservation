package com.theater.domain.theater.service;

import com.theater.domain.theater.Schedule;
import com.theater.domain.theater.ScheduleSearchCond;
import com.theater.domain.theater.dto.ScheduleShowDto;
import com.theater.domain.theater.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule findSchedule(Integer scheduleKey) {
        return scheduleRepository.findByKey(scheduleKey);
    }

    public List<ScheduleShowDto> findAllSchedule() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleShowDto> findAllSchedule(ScheduleSearchCond cond) {
        return scheduleRepository.findAll(cond);
    }

    public Schedule modify(Integer scheduleKey, Schedule schedule) {
        return scheduleRepository.update(scheduleKey, schedule);
    }

    public Boolean dropSchedule(Schedule schedule) {
        return scheduleRepository.delete(schedule);
    }

}
