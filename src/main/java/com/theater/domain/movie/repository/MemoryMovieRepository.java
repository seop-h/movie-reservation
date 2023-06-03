package com.theater.domain.movie.repository;

import com.theater.domain.movie.Movie;
import com.theater.domain.movie.MovieSearchCond;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryMovieRepository implements MovieRepository {

    private static final Map<Integer, Movie> store = new ConcurrentHashMap<>(); //영화 정보가 실제로 저장되는 장소(key: 영화 key, value: 영화 정보)
    private static Integer movieSequence = 0;

    @Override
    public Movie save(Movie movie) {
        movie.setKey(++movieSequence);
        store.put(movie.getKey(), movie);
        return store.get(movie.getKey());
    }

    @Override
    public Movie findByKey(Integer movieKey) {
        return store.get(movieKey);
    }

    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Movie> findAll(MovieSearchCond cond) {
        return store.values().stream()
                .filter(movie -> {
                    if (cond.getTitle() == null) {
                        return true;
                    } else{
                        return movie.getTitle().contains(cond.getTitle());
                    }
                }) //검색한 제목이 영화 제목에 포함되는 문자열인 값만 필터링
                .filter(movie -> {
                    if (cond.getDirector() == null) {
                        return true;
                    } else{
                        return movie.getDirector().contains(cond.getDirector());
                    }
                }) //검색한 감독이 영화감독에 포함되는 문자열인 값만 필터링
                .filter(movie -> {
                    if (cond.getActor() == null) return true;
                    for (String actor : movie.getActors()) {
                        if (actor.contains(cond.getActor())) {
                            return true;
                        }
                    }
                    return false;
                }) //영화 배우 리스트 중 검색한 배우 문자열을 포함하고 있는 값만 필터링
                .filter(movie -> {
                    if (cond.getGenre() == null) {
                        return true;
                    } else {
                        return movie.getGenre() == cond.getGenre();
                    }
                }) //검색한 장르가 영화장르와 같은 값만 필터링
                .collect(Collectors.toList()); //스트림 -> 리스트로 변환해서 반환
    }

    @Override
    public Movie update(Integer movieKey, Movie movie) {
        Movie findMovie = store.get(movieKey);
        updateMovieInfo(findMovie, movie);
        return findMovie;
    }

    @Override
    public Boolean delete(Movie movie) {
        if (store.remove(movie.getKey()) != null) return true;
        return false;
    }

    private void updateMovieInfo(Movie findMovie, Movie movie) {
        findMovie.setTitle(movie.getTitle());
        findMovie.setRunningTime(movie.getRunningTime());
        findMovie.setDirector(movie.getDirector());
        findMovie.setActors(movie.getActors());
        findMovie.setGenre(movie.getGenre());
        findMovie.setDescription(movie.getDescription());
        findMovie.setReleaseDate(movie.getReleaseDate());
    }
}
