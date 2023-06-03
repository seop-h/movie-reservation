package com.theater.domain.movie;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.DateFormat;
import java.util.List;

//영화 정보
//TODO 상영등급 추가 고려. enum으로 생성할 건지도 고려
@Data
@AllArgsConstructor
public class Movie {

    private Integer key; //영화번호
    private String title; //영화제목
    private Integer runningTime; //상영시간(분 단위)
    private String director; //감독
    private List<String> actors; //출연배우 리스트
    //TODO genre를 enum 클래스로 변경할지 생각
    private String genre; //장르
    private String description; //영화소개
    private DateFormat releaseDate; //개봉일

}
