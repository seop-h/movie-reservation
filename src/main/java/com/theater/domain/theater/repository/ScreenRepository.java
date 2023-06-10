package com.theater.domain.theater.repository;

import com.theater.domain.theater.Screen;

/**
 * 상영관 저장소 인터페이스(좌석 정보 포함)
 * 관리자만 접근 가능
 */
public interface ScreenRepository {

    Screen saveScreen(Screen screen); //상영관 추가(기본 좌석 수: 10개)

    Screen findScreenByKey(Integer screenKey); //상영관 조회

    Boolean deleteScreen(Screen screen); //상영관 삭제

    Integer addSeat(Integer screenKey, Integer seatCnt); //상영관에 seatCnt만큼 좌석 추가

}
