package com.theater.domain.movie.service;

import com.theater.domain.movie.Movie;
import com.theater.domain.movie.MovieSearchCond;
import com.theater.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie findMovie(Integer movieKey) {
        return movieRepository.findByKey(movieKey);
    }

    public List<Movie> findMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> findMovies(MovieSearchCond cond) {
        return movieRepository.findAll(cond);
    }

    public Movie modify(Integer movieKey, Movie movie) {
        return movieRepository.update(movieKey, movie);
    }

    public Boolean dropMovie(Movie movie) {
        return movieRepository.delete(movie);
    }


}
