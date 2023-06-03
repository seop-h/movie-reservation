package com.theater.domain.theater.repository;

import com.theater.domain.theater.Schedule;
import com.theater.domain.theater.ScheduleSearchCond;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryScheduleRepository implements ScheduleRepository {

    private static final Map<Integer, Schedule> store = new ConcurrentHashMap<>();
    private static Integer scheduleSequence = 0;

    @Override
    public Schedule save(Schedule schedule) {
        schedule.setKey(++scheduleSequence);
        store.put(schedule.getKey(), schedule);
        return store.get(schedule.getKey());
    }

    @Override
    public Schedule findByKey(Integer scheduleKey) {
        return store.get(scheduleKey);
    }

    @Override
    public List<Schedule> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Schedule> findAll(ScheduleSearchCond cond) {
        return store.values().stream()
                .filter(schedule -> {
                    if (cond.getMovieKey() == null) {
                        return true;
                    }
                    return (schedule.getMovieKey() == cond.getMovieKey());
                })
                .filter(schedule -> {
                    if (cond.getScreenKey() == null) {
                        return true;
                    }
                    return (schedule.getScreenKey() == cond.getScreenKey());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Schedule update(Integer scheduleKey, Schedule schedule) {
        Schedule findSchedule = store.get(scheduleKey);
        updateScheduleInfo(schedule, findSchedule);
        return findSchedule;
    }

    @Override
    public Boolean delete(Schedule schedule) {
        if (store.remove(schedule.getKey()) != null) return true;
        return false;
    }

    private void updateScheduleInfo(Schedule schedule, Schedule findSchedule) {
        findSchedule.setMovieKey(schedule.getMovieKey());
        findSchedule.setScreenKey(schedule.getScreenKey());
        findSchedule.setStartTime(schedule.getStartTime());
    }
}
