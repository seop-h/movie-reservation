package com.theater.domain.theater.service;

import com.theater.domain.theater.Screen;
import com.theater.domain.theater.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScreenService {

    private final ScreenRepository screenRepository;

    public Screen addScreen(Screen screen) {
        return screenRepository.saveScreen(screen);
    }

    public Screen findScreen(Integer screenKey) {
        return screenRepository.findScreenByKey(screenKey);
    }

    public Boolean dropScreen(Screen screen) {
        return screenRepository.deleteScreen(screen);
    }

    public Integer expand(Integer screenKey, Integer seatCnt) {
        return screenRepository.addSeat(screenKey, seatCnt);
    }

}
