package com.theater.domain.theater;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//상영관 정보
@Data
@NoArgsConstructor
public class Screen {

    private Integer key; //상영관 번호
    private List<Integer> seats; //상영관 내 좌석 (좌석번호로 저장)

}
