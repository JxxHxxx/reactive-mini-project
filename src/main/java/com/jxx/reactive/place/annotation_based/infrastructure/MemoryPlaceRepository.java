package com.jxx.reactive.place.annotation_based.infrastructure;

import com.jxx.reactive.place.annotation_based.domain.Place;
import com.jxx.reactive.place.annotation_based.domain.PlaceRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryPlaceRepository implements PlaceRepository {

    private static Map<Long, Place> store = new HashMap<>();

    @Override
    public Place save(Place place) {
        return store.put(place.getId(), place);
    }

    @Override
    public List<Place> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public Place findBy(Long id) {
        return store.get(id);
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
