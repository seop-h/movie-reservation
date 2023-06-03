package com.theater.domain.theater.repository;

import com.theater.domain.theater.Screen;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryScreenRepository implements ScreenRepository {

    private static final Map<Integer, Screen> store = new ConcurrentHashMap<>();
    private static Integer screenSequence = 0;

    @Override
    public Screen saveScreen(Screen screen) {
        screen.setKey(++screenSequence);
        List<Integer> seats = new ArrayList<>();
        for (int i = 1; i <= 10; i++) seats.add(i);
        screen.setSeats(seats);
        store.put(screen.getKey(), screen);
        return store.get(screen.getKey());
    }

    @Override
    public Screen findScreenByKey(Integer screenKey) {
        return store.get(screenKey);
    }

    @Override
    public Boolean deleteScreen(Screen screen) {
        if (store.remove(screen.getKey()) != null) return true;
        return false;
    }

    @Override
    public Integer addSeat(Integer screenKey, Integer seatCnt) {
        Screen screen = store.get(screenKey);
        List<Integer> seats = screen.getSeats();
        int size = seats.size();
        for (int i = size + 1; i <= size + seatCnt; i++) seats.add(i);
        screen.setSeats(seats); //TODO 이게 꼭 필요한 것?
        return seats.size();
    }
}
