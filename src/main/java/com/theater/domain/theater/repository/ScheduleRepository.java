package com.theater.domain.theater.repository;

import com.theater.domain.theater.Schedule;
import com.theater.domain.theater.ScheduleSearchCond;
import com.theater.domain.theater.dto.ScheduleShowDto;

import java.util.List;

/**
 * 상영일정 저장소 인터페이스
 * findAll을 제외하고 관리자만 접근 가능 (일반 회원은 X)
 */
public interface ScheduleRepository {

    Schedule save(Schedule schedule); //상영일정 추가

    Schedule findByKey(Integer scheduleKey); //상영일정 하나 조회

    List<ScheduleShowDto> findAll(); //상영일정 모두 조회

    List<ScheduleShowDto> findAll(ScheduleSearchCond cond); //조건(cond)에 맞는 상영일정만 조회

    Schedule update(Integer scheduleKey, Schedule schedule); //상영일정 수정

    Boolean delete(Schedule schedule); //상영일정 삭제

    void occupySeat(Schedule schedule, Integer seat);

}
