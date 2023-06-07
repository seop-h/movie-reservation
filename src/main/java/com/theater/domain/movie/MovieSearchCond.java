package com.theater.domain.movie;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieSearchCond {

    private String title; //영화제목
    private String director; //감독
    private String actor; //출연배우

}
