package com.theater.domain.movie;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

//영화 정보
//TODO 상영등급 추가 고려. enum으로 생성할 건지도 고려 -> 회원 클래스에 나이 필드가 추가되면 이것도 추가
@Data
public class Movie {

    private Integer key; //영화번호
    private String title; //영화제목
    private Integer runningTime; //상영시간(분 단위)
    private String director; //감독
    private List<String> actors; //출연배우 리스트
    private Genre genre; //장르
    private LocalDate releaseDate; //개봉일

    public Movie(String title, Integer runningTime, String director, List<String> actors, Genre genre, LocalDate releaseDate) {
        this.title = title;
        this.runningTime = runningTime;
        this.director = director;
        this.actors = actors;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }
}
