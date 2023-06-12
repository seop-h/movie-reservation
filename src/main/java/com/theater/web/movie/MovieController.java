package com.theater.web.movie;

import com.theater.domain.movie.Genre;
import com.theater.domain.movie.Movie;
import com.theater.domain.movie.MovieSearchCond;
import com.theater.domain.movie.service.MovieService;
import com.theater.domain.theater.Schedule;
import com.theater.web.responsedata.ResponseResult;
import com.theater.web.responsedata.extension.MovieResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    //로그인되지 않은 사용자 접근 시: 컨트롤러 관게없이 errorCode = -200

    private final MovieService movieService;

    @GetMapping //쿼리 파라미터로 받은 데이터에 해당하는 영화 조회. 쿼리 파라미터가 없으면 모든 영화 조회(에러 발생시 errorCode: -300번대)
    public ResponseResult findMovies(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String director,
                                     @RequestParam(required = false) String actor) {

        if (title == null && director == null && actor == null) { //쿼리 파라미터가 없으면 모든 영화 조회
            log.info("모든 영화 조회");
            return new MovieResult("영화 조회 성공", movieService.findMovies());
        }

        //쿼리 파라미터가 있으면 그 조건에 맞는 영화 조회
        log.info("조건에 맞는 영화 조회");
        MovieSearchCond cond = new MovieSearchCond(title, director, actor); //쿼리 파라미터에 따른 조건 설정
        return new MovieResult("영화 조회 성공", movieService.findMovies(cond)); //그 조건을 넘겨주며 영화 조회
    }

    @PostConstruct
    private void initMovieSave() {//초기 영화 데이터 세팅(메모리 사용 시 필요)
        log.info("[Movie] initialize Movie Repository");

        movieService.addMovie(new Movie("존 윅4", 169, "채드 스타헬스키",
                saveActors("키아누 리브스", "빌 스카스가드"), Genre.ACTION,
                LocalDate.parse("2023-04-12")));
        movieService.addMovie(new Movie("옥수역 귀신", 80, "정용기",
                saveActors("김보라", "김재현"), Genre.HORROR,
                LocalDate.parse("2023-04-19")));
        movieService.addMovie(new Movie("교섭", 108, "임순례",
                saveActors("황정민", "현빈"), Genre.THRILLER,
                LocalDate.parse("2023-01-16")));
        movieService.addMovie(new Movie("앤트맨과 와스프:퀀텀매니아", 124, "페이턴 리드",
                saveActors("폴 러드", "에반젤린 릴리"), Genre.ACTION,
                LocalDate.parse("2023-02-15")));
        movieService.addMovie(new Movie("던전 앤 드래곤", 134, "조너선 골드스틴",
                saveActors("크리스 파인", "미셸 로드리게스"), Genre.FANTASY,
                LocalDate.parse("2023-03-29")));
    }

    private ArrayList<String> saveActors(String... actors) {
        ArrayList<String> actorList = new ArrayList<>();
        for (String actor : actors) {
            actorList.add(actor);
        }
        return actorList;
    }

}
