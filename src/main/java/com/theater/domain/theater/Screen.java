package com.theater.domain.theater;

import lombok.AllArgsConstructor;
import lombok.Data;

//상영관 정보
@Data
@AllArgsConstructor
public class Screen {

    private Integer key; //상영관 번호
    private Integer seats; //좌석수

}
