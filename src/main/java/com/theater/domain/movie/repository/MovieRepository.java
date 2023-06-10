package com.theater.domain.movie.repository;

import com.theater.domain.movie.Movie;
import com.theater.domain.movie.MovieSearchCond;

import java.util.List;

/**
 * 영화 저장소 인터페이스
 * findAll을 제외하면 관리자만 접근 가능 (일반 회원 X)
 */
public interface MovieRepository {

    Movie save(Movie movie); //영화 추가

    Movie findByKey(Integer movieKey); //key로 영화 조회

    List<Movie> findAll(); //모든 영화 조회

    List<Movie> findAll(MovieSearchCond cond); //조건(cond)에 맞는 영화만 조회

    Movie update(Integer movieKey, Movie movie); //영화 정보 수정

    Boolean delete(Movie movie); //영화 삭제

}
