package com.theater.web.movie;

import com.theater.domain.movie.Genre;
import com.theater.domain.movie.Movie;
import com.theater.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @PostConstruct
    private void initMovieSave() {//초기 영화 데이터 세팅(메모리 사용 시 필요)

        movieService.addMovie(new Movie("존 윅4", 169, "채드 스타헬스키",
                saveActors("키아누 리브스", "빌 스카스가드"), Genre.ACTION,
                new Date(2023, 4, 12)));
        movieService.addMovie(new Movie("옥수역 귀신", 80, "정용기",
                saveActors("김보라", "김재현"), Genre.HORROR,
                new Date(2023, 4, 19)));
        movieService.addMovie(new Movie("교섭", 108, "임순례",
                saveActors("황정민", "현빈"), Genre.THRILLER,
                new Date(2023, 1, 16)));
        movieService.addMovie(new Movie("앤트맨과 와스프:퀀텀매니아", 124, "페이턴 리드",
                saveActors("폴 러드", "에반젤린 릴리"), Genre.ACTION,
                new Date(2023, 2, 15)));
        movieService.addMovie(new Movie("던전 앤 드래곤", 134, "조너선 골드스틴",
                saveActors("크리스 파인", "미셸 로드리게스"), Genre.FANTASY,
                new Date(2023, 3, 29)));
    }

    private ArrayList<String> saveActors(String... actors) {
        ArrayList<String> actorList = new ArrayList<>();
        for (String actor : actors) {
            actorList.add(actor);
        }
        return actorList;
    }

}
