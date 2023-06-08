package com.theater.domain.theater.dto;

import com.theater.domain.movie.Movie;
import com.theater.domain.movie.service.MovieService;
import com.theater.domain.theater.Schedule;
import com.theater.domain.theater.Screen;
import com.theater.domain.theater.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleRegister {

    private final MovieService movieService;
    private final ScreenService screenService;

    public List<ScheduleShowDto> changeToShowList(List<Schedule> scheduleList) {
        List<ScheduleShowDto> dtoList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            dtoList.add(changeToShow(schedule));
        }
        return dtoList;
    }

    private ScheduleShowDto changeToShow(Schedule schedule) {
        Movie movie = movieService.findMovie(schedule.getMovieKey());
        Screen screen = screenService.findScreen(schedule.getScreenKey());

        return new ScheduleShowDto(
                schedule.getKey(),
                schedule.getMovieKey(),
                movie.getTitle(),
                movie.getRunningTime(),
                movie.getGenre(),
                movie.getReleaseDate(),
                schedule.getScreenKey(),
                screen.getSeats().size(),
                schedule.getStartTime()
        );
    }

}
