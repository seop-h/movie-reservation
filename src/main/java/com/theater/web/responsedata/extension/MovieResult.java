package com.theater.web.responsedata.extension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.theater.domain.movie.Movie;
import com.theater.web.responsedata.ResponseResult;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) //Json 데이터로 반환할 떄 null인 필드는 표시하지 않음
public class MovieResult extends ResponseResult {

    private List<Movie> movies;

    public MovieResult(String message, List<Movie> movies) {
        super(message);
        this.movies = movies;
    }
}
