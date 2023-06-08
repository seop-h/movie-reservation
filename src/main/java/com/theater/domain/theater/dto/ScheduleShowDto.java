package com.theater.domain.theater.dto;

import com.theater.domain.movie.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
TODO GET /theater 요청이 올 때마다 Schedule 클래스를 이 클래스로 변환시키는 게 나은지,
     아예 Schedule 클래스 스펙을 이 클래스처럼 바꿔서 repository에 저장하는 게 나은지 생각
     후자처럼 하면 ScheduleShowDto와 ScheduleRegister가 필요없지만, 나중에 데이터베이스를 사용하면 정규화가 되지 않은 db를 사용하는 것
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScheduleShowDto {

    private Integer key; //상영일정번호

    //영화 정보
    private Integer movieKey; //영화번호
    private String title; //제목
    private Integer runningTime; //상영시간(분 단위)
    private Genre genre; //장르
    private LocalDate releaseDate; //개봉일

    //상영관 정보
    private Integer screenKey; //상영관 번호
    //TODO 해당 상영일정에 남은 좌석 수 표시로 변경
    private Integer seats; //상영관 전체 좌석 수

    private LocalDateTime startTime; //상영시작일
}
